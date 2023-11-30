package mytunes.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MediaPlayerWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("MediaPlayer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}