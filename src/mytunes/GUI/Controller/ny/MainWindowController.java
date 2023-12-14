package mytunes.GUI.Controller.ny;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import mytunes.GUI.Controller.ny.Containers.LibraryContainer;
import mytunes.GUI.Controller.ny.Pages.SearchController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private VBox librarySection;

    @FXML
    private BorderPane mainWindow;

    @FXML
    private VBox mainSection;

    @FXML
    private VBox mainShit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadLibrarySection();
            loadMediaPlayerSection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void switchView(Parent view) {
        System.out.println("Switching View");
        mainWindow.setCenter(view);
    }

    private void loadLibrarySection() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/new/containers/Library.fxml"));
        VBox vbox = fxmlLoader.load();

        librarySection.getChildren().add(vbox);

        LibraryContainer libraryContainer = fxmlLoader.getController();
        libraryContainer.setMainWindowController(this);
        libraryContainer.LoadPlaylistSongsView(mainWindow);
    }

    private void loadMediaPlayerSection() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/new/containers/MediaPlayer.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();

        mainWindow.setBottom(anchorPane);
    }

    public void btnHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/new/pages/Home.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();

        switchView(anchorPane);
    }

    public void btnSearch(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/new/pages/Search.fxml"));
        GridPane gridPane = fxmlLoader.load();

        SearchController searchController = fxmlLoader.getController();
        searchController.loadAlbumView(mainWindow);
        searchController.setMainWindowController(this);

        switchView(gridPane);
    }
}
