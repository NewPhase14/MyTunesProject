package mytunes.GUI.Controller.ny.PopUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import mytunes.BE.Artist;
import mytunes.BE.Song;
import mytunes.GUI.Model.ArtistModel;
import mytunes.GUI.Model.MediaPlayerModel;
import mytunes.GUI.Model.SongImportModel;
import mytunes.GUI.Model.SongModel;

import java.io.File;

public class ImportSongController {

    @FXML
    private ImageView imgPreview;

    @FXML
    private Button btnImportFX;

    @FXML
    private TextField txtTitel, txtArtist, txtTime, txtFile, txtPicture, txtFeature;

    @FXML
    private ComboBox dropCategory;

    private SongImportModel songImportModel;
    private MediaPlayerModel mediaPlayerModel;
    private ArtistModel artistModel;
    private SongModel songModel;

    private String artistID, artistName, artistAlias;
    private String songID, songTitle, songArtist, songAlbum, songfilePath;

    private File selectedFile;

    public ImportSongController() throws Exception {
        songImportModel = new SongImportModel();
        mediaPlayerModel = new MediaPlayerModel();
        artistModel = new ArtistModel();
        songModel = new SongModel();
    }

    private void setDisabled(boolean disabled) {
        txtArtist.setDisable(disabled);
        txtTitel.setDisable(disabled);
        txtFeature.setDisable(disabled);
        txtFile.setDisable(disabled);
        txtTime.setDisable(disabled);
        dropCategory.setDisable(disabled);
        txtPicture.setDisable(disabled);
        btnImportFX.setDisable(disabled);
    }

    private void setPreviewImg(String url) {
        if (url == null)
            return;

        Image image = new Image(url);
        imgPreview.setImage(image);
    }

    public void btnChoose(ActionEvent actionEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Audio Files (*.mp3, *.wav)", "*.mp3", "*.wav");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setTitle("Select Music File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            songImportModel.searchSong(selectedFile.getName());

            txtArtist.setText(songImportModel.getArtist());
            txtTitel.setText(songImportModel.getTitle());
            txtFile.setText(selectedFile.getAbsolutePath());

            dropCategory.getItems().addAll(songImportModel.getGenre());
            dropCategory.getSelectionModel().selectFirst();

            String imageURL = songImportModel.getPictureURL();

            setPreviewImg(imageURL);
            txtPicture.setText(imageURL);

            Media media = new Media(selectedFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(() -> {
                Duration duration = media.getDuration();
                int millis = (int) duration.toSeconds();

                txtTime.setText(mediaPlayerModel.getTimeFromDouble(millis));
            });

            setDisabled(false);

        } else {
            System.out.println("file is not valid");
        }

    }

    public void btnChoosePicture(ActionEvent actionEvent) {
    }

    public void btnSearch(ActionEvent actionEvent) throws Exception {
        songImportModel.searchSongFromText(txtArtist.getText(), txtTitel.getText());

        if (songImportModel.holdsData()) {
            txtTitel.setText(songImportModel.getTitle());
            txtArtist.setText(songImportModel.getArtist());

            txtFeature.setText(songImportModel.getFeatures());
            String pic = songImportModel.getPictureURL();
            dropCategory.getItems().addAll(songImportModel.getGenre());
            txtPicture.setText(pic);

            setPreviewImg(pic);

            setDisabled(false);
         }
    }

    public void btnImport(ActionEvent actionEvent) throws Exception {
        artistID = songImportModel.getArtistID();
        artistName = songImportModel.getArtist();
        artistAlias = songImportModel.getAlias();

        songID = songImportModel.getSongID();
        songTitle = songImportModel.getTitle();
        songArtist = songImportModel.getArtist();
        songAlbum = "none";
        songfilePath = selectedFile.getPath();

        Artist artist = artistModel.createArtist(new Artist(artistID, artistName, artistAlias));
        System.out.println(artist.getPrimaryID());
        songModel.createNewSong(new Song(songID, songTitle, artist.getPrimaryID(), songAlbum, songfilePath, txtPicture.getText()));
    }
}
