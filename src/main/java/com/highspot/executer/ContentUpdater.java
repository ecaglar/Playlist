package com.highspot.executer;

import com.highspot.model.Content;
import com.highspot.model.Playlist;

/**
 * Wrapper for interim data structure and provides
 * methods to modify underlying structure.
 * Changes  provided by change file applied using these
 * helper methods.
 */
public class ContentUpdater {

    private Content content;

    public Content getContent() {
        return content;
    }
    public void setContent(Content content) {
        this.content = content;
    }
    public void addPlaylist(final Playlist playlist) {
        content.addPlaylist(playlist);
    }

    public void removePlaylist(final int playlistId) {
        content.removePlaylist(playlistId);
    }

    public void addSongToPlaylist(final int songId, final int playlistId) {
        content.addSongToPlaylist(songId, playlistId);
    }
}
