package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;

public class Background_controller implements Initializable {
    @FXML
    private Button DeleteButton;

    @FXML
    private ListView<String> DictionaryList;

    @FXML
    private Button EditButton;

    @FXML
    private Button SearchButton;

    @FXML
    private Button SoundButton;

    @FXML
    private Button addButton;

    @FXML
    private ImageView addView;

    @FXML
    private ImageView editView;

    @FXML
    private Label myLabel;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView searchView;

    @FXML
    private Button starButton;

    @FXML
    private ImageView starView;

    private final DictionaryManagement dictManagement = new DictionaryManagement();
    private final Dictionary newDict = dictManagement.getDictionary();
    private final List<Word> newWords = newDict.getWords();
    String input;
    Word currentWord;

    public void handleImage() {
        System.out.println("Click the image");
    }

    // the list of words
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            newWords.clear();
            dictManagement.insertFromFile();
            if (input == null) {
                DictionaryList.getItems().clear();
                for (Word newWord : newWords) {
                    DictionaryList.getItems().add(newWord.getWord_target());
                }
                DictionaryList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                    int index = DictionaryList.getSelectionModel().getSelectedIndex();
                    currentWord = newWords.get(index);
                    myLabel.setText(newWords.get(index).getWord_target() + "\n" + newWords.get(index).getWord_explain());
                });
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    // make a box to search word
    public void submit() {
        input = searchField.getText();
        try {
            dictManagement.clear_SearchList();
            DictionaryList.getItems().clear();
            dictManagement.dictionarySeacher(input);
            List<Word> searchWordList = dictManagement.getSearchList();
            for (Word newWord : searchWordList) {
                DictionaryList.getItems().add(newWord.getWord_target());
            }
            DictionaryList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                int index = DictionaryList.getSelectionModel().getSelectedIndex();
                currentWord = searchWordList.get(index);
                myLabel.setText(searchWordList.get(index).getWord_target() + "\n" + searchWordList.get(index).getWord_explain());
            });
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public void removeWord() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Remove the word: " + currentWord.getWord_target() +" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        if(result.get() ==ButtonType.OK){
            dictManagement.removeWord(currentWord);
        }
        submit();
    }

    public void addAWord() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add a word");
        dialog.setHeaderText("Add a new word to dictionary");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));

        TextField word = new TextField();
        word.setPromptText("Word to add");
        word.setPrefWidth(200);
        TextArea meaning = new TextArea();
        meaning.setPromptText("Meaning of word");
        meaning.setPrefHeight(300);
        meaning.setPrefWidth(200);

        grid.add(new Label("Word"), 0, 0);
        grid.add(word, 1, 0);
        grid.add(new Label("Meaning"), 0, 1);
        grid.add(meaning, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == ButtonType.OK) {
            Word newWord = new Word(word.getText(), meaning.getText());
            if (Objects.equals(word.getText(), "")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Word field is empty");
                alert.showAndWait();
            } else if (Collections.binarySearch(newWords, newWord) < 0) {
                dictManagement.insertSingleWord(newWord);
                newDict.sortWords();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Word added successfully");
                alert.showAndWait();
                submit();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Word already present");
                alert.showAndWait();
            }
        }
    }

    public void modifyWord() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modify Word");
        dialog.setHeaderText("Modify a word");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));

        TextField word = new TextField();
        word.setPromptText("Word to modify");
        word.setText(currentWord.getWord_target());
        word.setPrefWidth(200);
        TextArea meaning = new TextArea();
        meaning.setPromptText("Meaning of word");
        meaning.setText(currentWord.getWord_explain());
        meaning.setPrefHeight(300);
        meaning.setPrefWidth(200);

        grid.add(new Label("Word"), 0, 0);
        grid.add(word, 1, 0);
        grid.add(new Label("Meaning"), 0, 1);
        grid.add(meaning, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.get() == ButtonType.OK){
            if(Objects.equals(word.getText(),"")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Word field is empty");
                alert.showAndWait();
            }
            else{
                int index = Collections.binarySearch(newWords, currentWord);
                newWords.get(index).setWord_target(word.getText());
                newWords.get(index).setWord_explain(meaning.getText());
                newDict.sortWords();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Word modified");
                alert.showAndWait();
                submit();
            }
        }
    }
}
