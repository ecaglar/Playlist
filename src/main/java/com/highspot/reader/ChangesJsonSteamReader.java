package com.highspot.reader;

import com.google.gson.stream.JsonReader;
import com.highspot.exception.ReadFailedException;
import com.highspot.executer.ContentUpdater;
import com.highspot.model.Content;
import com.highspot.model.Playlist;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

/**
 * Change file reader.It reads the change file and instantly reflects to content.
 * Playlist remove action executed last so it overrides the previous changes.
 * If you try to add a new playlist with an existing id then it will be discarded.
 */
public class ChangesJsonSteamReader implements IReader<Content> {

    @Autowired
    private ContentUpdater contentUpdater;

    @Override
    public Content read(Reader in) {
        JsonReader jsonReader = new JsonReader(in);
        Set<Integer> playlistsToBeRemoved = new HashSet<>();

        try {

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if ("playlists".equals(name)) {
                    readPlaylists(contentUpdater, playlistsToBeRemoved, jsonReader);
                } else if ("songs".equals(name)) {
                    readSongUpdates(contentUpdater, jsonReader);
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.close();
        } catch (IOException e) {
            throw new ReadFailedException(e.getMessage(), e);
        }
        for (Integer id : playlistsToBeRemoved) {
            contentUpdater.removePlaylist(id);
        }

        return null;
    }

    private void readPlaylists(ContentUpdater contentUpdater, Set<Integer> removedSongs, JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {

            String playlistName = jsonReader.nextName();
            if ("add".equals(playlistName)) {
                readAddPlaylists(contentUpdater, jsonReader);
            } else if ("remove".equals(playlistName)) {
                readRemovePlaylists(removedSongs, jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }
    private void readSongUpdates(ContentUpdater contentUpdater, JsonReader jsonReader) throws IOException {
        String name;
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            Integer songId = null;
            Integer playlistId = null;
            while (jsonReader.hasNext()) {
                name = jsonReader.nextName();
                if ("song_id".equals(name)) {
                    songId = jsonReader.nextInt();
                } else if ("playlist_id".equals(name)) {
                    playlistId = jsonReader.nextInt();
                } else {
                    jsonReader.skipValue();
                }
            }
            if (songId != null && playlistId != null) {
                contentUpdater.addSongToPlaylist(songId, playlistId);
            }
            jsonReader.endObject();
        }
        jsonReader.endArray();
    }
    private void readAddPlaylists(ContentUpdater contentUpdater, JsonReader jsonReader) throws IOException {
        String name;
        Playlist playlist;

        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            Integer id = null;
            Integer user_id = null;
            Set<Integer> song_ids = new HashSet<>();
            Integer song_id;

            while (jsonReader.hasNext()) {
                name = jsonReader.nextName();
                if ("id".equals(name)) {
                    id = jsonReader.nextInt();
                } else if ("user_id".equals(name)) {
                    user_id = jsonReader.nextInt();
                } else if ("song_ids".equals(name)) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        song_id = jsonReader.nextInt();
                        song_ids.add(song_id);
                    }
                    jsonReader.endArray();
                } else {
                    jsonReader.skipValue();
                }
            }
            if (id != null && user_id != null && song_ids.size() > 0) {
                playlist = new Playlist(id, user_id, song_ids);
                contentUpdater.addPlaylist(playlist);
            }
            jsonReader.endObject();
        }
        jsonReader.endArray();
    }
    private void readRemovePlaylists(Set<Integer> removedSongs, JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            Integer id = jsonReader.nextInt();
            removedSongs.add(id);
        }
        jsonReader.endArray();
    }
}
