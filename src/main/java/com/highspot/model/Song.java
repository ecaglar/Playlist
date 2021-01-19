package com.highspot.model;

import java.util.Objects;

/**
 * song model.
 * if id is same then it is same song.
 */
public class Song {

    private int id;
    private String title;
    private String artist;

    public Song(int id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
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
        Song song = (Song) o;
        return id == song.id;
    }
    @Override
    public String toString() {
        return "Song{" + "id=" + id + ", title='" + title + '\'' + ", artist='" + artist + '\'' + '}';
    }
}
