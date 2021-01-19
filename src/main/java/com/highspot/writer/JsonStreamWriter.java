package com.highspot.writer;

import com.google.gson.stream.JsonWriter;
import com.highspot.exception.WriteFailedException;
import com.highspot.model.Content;
import com.highspot.model.Playlist;
import com.highspot.model.Song;
import com.highspot.model.User;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

/**
 * Stream writer for output file.
 * It uses gson streams for min memory footprint.
 */
public class JsonStreamWriter implements IWriter<Content> {

    @Override
    public void writeFile(Writer out, Content content) {

        JsonWriter jsonWriter = new JsonWriter(out);
        jsonWriter.setIndent("  ");

        try {
            jsonWriter.beginObject();
            writeUsers(jsonWriter, content.getUsers());
            writePlaylists(jsonWriter, content.getPlaylists());
            writeSongs(jsonWriter, content.getSongs());
            jsonWriter.endObject();
            jsonWriter.close();
        } catch (IOException e) {
            throw new WriteFailedException(e.getMessage(), e);
        }
    }
    private void writeUsers(final JsonWriter jsonWriter, final Set<User> users) throws IOException {
        jsonWriter.name("users");
        jsonWriter.beginArray();
        for (User u : users) {
            writeUser(jsonWriter, u);
        }
        jsonWriter.endArray();
    }
    private void writePlaylists(JsonWriter jsonWriter, Map<Integer, Playlist> playlists) throws IOException {
        jsonWriter.name("playlists");
        jsonWriter.beginArray();
        for (Playlist playlist : playlists.values()) {
            writePlaylist(jsonWriter, playlist);
        }
        jsonWriter.endArray();
    }
    private void writeSongs(JsonWriter jsonWriter, Map<Integer, Song> songs) throws IOException {
        jsonWriter.name("songs");
        jsonWriter.beginArray();
        for (Song s : songs.values()) {
            writeSong(jsonWriter, s);
        }
        jsonWriter.endArray();
    }
    private void writeUser(final JsonWriter jsonWriter, final User user) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id").value(user.getId());
        jsonWriter.name("name").value(user.getName());
        jsonWriter.endObject();
    }
    private void writePlaylist(JsonWriter jsonWriter, Playlist playlist) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id").value(playlist.getId());
        jsonWriter.name("user_id").value(playlist.getUser_id());
        jsonWriter.name("song_ids");
        jsonWriter.beginArray();
        for (Integer integer : playlist.getSong_ids()) {
            jsonWriter.value(integer);
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }
    private void writeSong(JsonWriter jsonWriter, Song s) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id").value(s.getId());
        jsonWriter.name("artist").value(s.getArtist());
        jsonWriter.name("title").value(s.getTitle());
        jsonWriter.endObject();
    }
}
