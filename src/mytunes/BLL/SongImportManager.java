package mytunes.BLL;

import mytunes.DAL.REST.CoverArt;
import mytunes.BE.REST.Release;
import mytunes.DAL.REST.MusicBrainzConnector;
import org.json.JSONException;

import java.util.List;

public class SongImportManager {

    private MusicBrainzConnector musicBrainzConnector;
    private CoverArt coverArt;

    public SongImportManager() {
        musicBrainzConnector = new MusicBrainzConnector();
    }

    private String removeExtension(String name) {
        return name.substring(0, name.length() - 4);
    }

    private String getArtistFromStr(String name) {
        String[] parts = name.split("-");
        return parts[0].trim();
    }

    private String getTitleFromStr(String name) {
        String[] parts = name.split("-");
        return parts[1].trim();
    }

    public void searchSong(String name) throws Exception {
        String newStr = removeExtension(name);

        musicBrainzConnector.searchSong(getArtistFromStr(newStr), getTitleFromStr(newStr));
    }

    public String getArtist() throws Exception {
        return musicBrainzConnector.getArtist();
    }

    public String getTitle() throws Exception {
        return musicBrainzConnector.getTitle();
    }

    public List<String> getGenre() throws JSONException {
        return musicBrainzConnector.getGenre();
    }

    public String getPictureURL() throws Exception {
        coverArt = new CoverArt(musicBrainzConnector.getPictureID());
        return coverArt.getFrontThumbnail();
    }

    public boolean searchSongFromText(String artist, String title) throws Exception {
        return musicBrainzConnector.searchSong(artist, title);
    }

    public boolean holdsData() throws Exception {
        return musicBrainzConnector.holdsData();
    }

    public String getFeatures() throws JSONException {
        return musicBrainzConnector.getFeatures();
    }

    public String getAlias() throws JSONException {
        return musicBrainzConnector.getArtistAlias();
    }

    public String getArtistID() throws JSONException {
        return musicBrainzConnector.getArtistID();
    }

    public String getSongID() throws JSONException {
        return musicBrainzConnector.getSongID();
    }

    public List<Release> getAlbums() throws JSONException {
        return musicBrainzConnector.getAlbums();
    }
}
