package com.highspot.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** wrapper for top structures includes playlists,users and songs. */
public class Content {

  private final Set<User> users;
  private final Map<Integer, Playlist> playlists;
  private final Map<Integer, Song> songs;
  Logger LOG = LogManager.getLogger(Content.class);

  public Content() {
    users = new HashSet<>();
    playlists = new HashMap<>();
    songs = new HashMap<>();
  }

  public void addUser(final User u) {
    users.add(u);
    LOG.info("User added: " + u.toString());
  }

  public void addSong(final Song s) {
    LOG.info("Song will be added: " + s.toString());
    Song added = songs.putIfAbsent(s.getId(), s);
    if (added == s) {
      LOG.info("Song added: " + s.toString());
    } else {
      LOG.info("Song not added. There is already a song with same id: " + s.toString());
    }
  }

  public void addPlaylist(final Playlist p) {
    LOG.info("Playlist will be added: " + p.toString());
    Playlist added = playlists.putIfAbsent(p.getId(), p);
    if (added == p) {
      LOG.info("Playlist added: " + p.toString());
    } else {
      LOG.info("Playlist not added. There is already a playlist with same id: " + p.toString());
    }
  }

  public void removePlaylist(final int id) {
    playlists.remove(id);
    LOG.info("Playlist removed with id: " + id);
  }

  public void addSongToPlaylist(final int songId, final int playlistId) {
    if (songs.containsKey(songId) && playlists.containsKey(playlistId)) {
      playlists.get(playlistId).addSong(songId);
      LOG.info("Song: " + songId + " added to playlist : " + playlistId);
    } else {
      LOG.info(
          "Song: "
              + songId
              + " could not be added to playlist : "
              + playlistId
              + " Either playlsit or song does not exist");
    }
  }

  public Set<User> getUsers() {
    return users;
  }

  public Map<Integer, Playlist> getPlaylists() {
    return playlists;
  }

  public Map<Integer, Song> getSongs() {
    return songs;
  }
}
