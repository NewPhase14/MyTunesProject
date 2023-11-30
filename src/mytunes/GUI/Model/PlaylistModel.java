package mytunes.GUI.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.BE.Playlist;
import mytunes.BE.Song;
import mytunes.BLL.PlaylistManager;

import java.io.IOException;
import java.util.List;

public class PlaylistModel {
    private ObservableList<Playlist> playlistToBeViewed;
    private ObservableList<Song> SongsToBeViewed;

    private PlaylistManager playlistManager;

    public PlaylistModel() throws Exception {
        playlistManager = new PlaylistManager();
        playlistToBeViewed = FXCollections.observableArrayList();
        playlistToBeViewed.addAll(getPlaylists());
    }

    public ObservableList<Song> getObservableSongs(Playlist playlist) throws Exception {
        SongsToBeViewed = FXCollections.observableArrayList();

        if (getSongs(playlist) == null)
            return SongsToBeViewed;

        SongsToBeViewed.addAll(getSongs(playlist));

        return SongsToBeViewed;
    }

    public ObservableList<Playlist> getObservablePlaylists() {
        return playlistToBeViewed;
    }

    public List<Playlist> getPlaylists() throws Exception {
        return playlistManager.getAllPlaylists();
    }

    public List<Song> getSongs(Playlist playlist) throws Exception {
        return playlistManager.getPlaylistSongs(playlist);
    }

    public void addSongToPlaylist(Playlist playlist, Song song) throws Exception {
        playlistManager.addSongToPlaylist(playlist, song);
    }

    public void createPlaylist(String name) throws Exception {
        playlistManager.createPlaylist(new Playlist(name));
    }
}
