package com.highspot.model;

import java.io.InvalidObjectException;
import java.util.Objects;
import java.util.Set;

/**
 * Wrapper for playlist.
 * If playlist id is same then it is the same playlist for us.
 */
public class Playlist {

    private int id;
    private int user_id;
    private Set<Integer> song_ids;

    public Playlist(int id, int user_id, Set<Integer> songs) throws InvalidObjectException {
        this.id = id;
        this.user_id = user_id;
        if (songs == null || songs.size() == 0) {
            throw new InvalidObjectException("Should have at least 1 song");
        }
        song_ids = songs;
    }

    void addSong(final int song_id) {
        song_ids.add(song_id);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public Set<Integer> getSong_ids() {
        return song_ids;
    }
    public void setSong_ids(Set<Integer> song_ids) {
        this.song_ids = song_ids;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Playlist playlist = (Playlist) o;
        return id == playlist.id;
    }
    @Override
    public String toString() {
        return "Playlist{" + "id=" + id + ", user_id=" + user_id + ", songs=" + song_ids + '}';
    }
}
