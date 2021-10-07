package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class DictionaryApplication extends Application {

    @Override
    public void start(Stage stage) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello.fxml")));
            stage.setTitle("New Window");
            stage.setResizable(false);
            Scene scene = new Scene(root);
            //scene.getStylesheets().add("foo/background.css");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
