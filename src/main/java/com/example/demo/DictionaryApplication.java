package com.example.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class DictionaryApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("dictUI.fxml")));
        Parent root = loader.load();
        UI_controller controller = loader.getController();

        stage.setTitle("MeowDict");
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e-> {
            try {
                controller.exportDataOnExit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
