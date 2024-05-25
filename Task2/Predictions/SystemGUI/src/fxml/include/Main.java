package fxml.include;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class Main extends Application {

    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/fxml/include/Main.fxml");
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Predictions");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
