package com.highspot.reader;

import com.google.gson.stream.JsonReader;
import com.highspot.exception.ReadFailedException;
import com.highspot.model.Content;
import com.highspot.model.Playlist;
import com.highspot.model.Song;
import com.highspot.model.User;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

/**
 * Stream reader for inout file.
 * Instead of reading all file into the memory, it streams the file and uses tokens.
 * So it uses min memory especially for large files.
 */
public class MixJsonStreamReader implements IReader<Content> {

    @Override
    public Content read(final Reader in) {

        JsonReader jsonReader = new JsonReader(in);
        Content content = new Content();
        try {
            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if ("users".equals(name)) {
                    readUsers(content, jsonReader);
                } else if ("playlists".equals(name)) {
                    readPlaylists(content, jsonReader);
                } else if ("songs".equals(name)) {
                    readSongs(content, jsonReader);
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            jsonReader.close();
        } catch (IOException e) {
            throw new ReadFailedException(e.getMessage(), e);
        }
        return content;
    }

    private void readUsers(Content content, JsonReader jsonReader) throws IOException {
        String name;
        User user;
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            Integer user_id = null;
            String user_name = null;
            while (jsonReader.hasNext()) {
                name = jsonReader.nextName();
                if ("id".equals(name)) {
                    user_id = jsonReader.nextInt();
                } else if ("name".equals(name)) {
                    user_name = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
                if (user_id != null && user_name != null) {
                    user = new User(user_id, user_name);
                    content.addUser(user);
                }
            }
            jsonReader.endObject();
        }
        jsonReader.endArray();
    }
    private void readPlaylists(Content content, JsonReader jsonReader) throws IOException {
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
                content.addPlaylist(playlist);
            }
            jsonReader.endObject();
        }
        jsonReader.endArray();
    }
    private void readSongs(Content content, JsonReader jsonReader) throws IOException {
        String name;
        Song song;
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            Integer id = null;
            String artist = null;
            String title = null;
            while (jsonReader.hasNext()) {
                name = jsonReader.nextName();
                if ("id".equals(name)) {
                    id = jsonReader.nextInt();
                } else if ("artist".equals(name)) {
                    artist = jsonReader.nextString();
                } else if ("title".equals(name)) {
                    title = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
                if (id != null && artist != null && title != null) {
                    song = new Song(id, title, artist);
                    content.addSong(song);
                }
            }
            jsonReader.endObject();
        }
        jsonReader.endArray();
    }
}
