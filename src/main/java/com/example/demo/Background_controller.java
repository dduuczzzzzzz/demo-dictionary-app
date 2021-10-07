package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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

    private final DictionaryManagement dictManagement =new DictionaryManagement();
    private final Dictionary newDict = dictManagement.getDictionary();
    private final List<Word> newWords = newDict.getWords();
    String input;
    Word currentWord;

    public void handleImage(){
        System.out.println("Click the image");
    }

    // the list of words
    public void initialize(URL arg0, ResourceBundle arg1){
        try {
            newWords.clear();
            dictManagement.insertFromFile();
            if(input == null){
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
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    // make a box to search word
    public void submit(){
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
        }
        catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public void removeWord(){
        dictManagement.removeWord(currentWord.getWord_target());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Remove word");
        alert.setHeaderText(null);
        alert.setContentText("Removed the word: " + currentWord.getWord_target());
        alert.showAndWait();
    }

    public void modifyWord(){

    }
}
