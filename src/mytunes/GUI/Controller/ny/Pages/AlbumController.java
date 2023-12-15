package mytunes.GUI.Controller.ny.Pages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mytunes.BE.Album;
import mytunes.BE.Song;
import mytunes.GUI.Controller.ny.Custom.TableContextMenu;
import mytunes.GUI.Controller.ny.Custom.TitleArtistCell;
import mytunes.GUI.Model.AlbumModel;
import mytunes.GUI.Model.MediaPlayerModel;
import mytunes.GUI.Model.PlaylistModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AlbumController implements Initializable {

    @FXML
    private ImageView imgCover;
    @FXML
    private Label lblType, lblName, lblArtistName, lblRelease, lblSongsAmount, lblPlayTime;

    @FXML
    private TableView<Song> tblSongsAlbum;
    @FXML
    private TableColumn<Song, Integer> columnPos;
    @FXML
    private TableColumn<Song, String> columnTitle;
    @FXML
    private TableColumn<Song, String> columnGenre;
    @FXML
    private TableColumn<Song, String> columnDuration;

    private AlbumModel albumModel;
    private MediaPlayerModel mediaPlayerModel;
    private PlaylistModel playlistModel;

    public AlbumController() throws Exception {
        this.albumModel = AlbumModel.getInstance();
        this.mediaPlayerModel = MediaPlayerModel.getInstance();

        mediaPlayerModel.setPlaylistSongs(tblSongsAlbum);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAlbumSongsTableView();
        enableRightClick();
    }

    private void enableRightClick() {
        tblSongsAlbum.setRowFactory(tv -> {
            TableRow<Song> row = new TableRow<>();
            try {
                TableContextMenu tableContextMenu = new TableContextMenu(tblSongsAlbum, null);
                tableContextMenu.createContextMenu(row);
            } catch (Exception e) {
                System.out.println("Cannot create ContextMenu for Playlists");
                throw new RuntimeException(e);
            }
            return row;
        });
    }

    private void setupAlbumSongsTableView() {
        columnTitle.setCellFactory(col -> new TitleArtistCell());
        columnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        columnDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        columnPos.setCellValueFactory(new PropertyValueFactory<>("orderID"));
    }

    private void updatePicture(Album album) {
        Image newImage = new Image(new File(album.getPictureURL()).toURI().toString());
        imgCover.setFitWidth(220);
        imgCover.setFitHeight(220);
        imgCover.setPreserveRatio(false);

        imgCover.setImage(newImage);
    }

    private void updateLabels(Album album) throws Exception {
        lblName.setText(album.getTitle());
        lblArtistName.setText(album.getArtist());
        lblType.setText(album.getType());
        lblRelease.setText(album.getReleaseDate());
        lblPlayTime.setText(albumModel.getAllPlayTime(album));

        String albumSongs = Integer.toString(album.getAlbumSongs().size());

        lblSongsAmount.setText(albumSongs + " sange");
    }

    private void updateUI(Album album) throws Exception {
        updateLabels(album);
        updatePicture(album);
    }

    public void tableAlbumSongs(Album album) throws Exception {
        if (album == null)
            return;

        tblSongsAlbum.refresh();
        tblSongsAlbum.setItems(albumModel.getObservableSongs(album));
        mediaPlayerModel.wasClickedTable(tblSongsAlbum);

        updateUI(album);
    }

    public void btnPageBack(ActionEvent actionEvent) {
    }

    public void btnNextPage(ActionEvent actionEvent) {
    }

    public void btnPlayPlaylist(ActionEvent actionEvent) {
    }
}
