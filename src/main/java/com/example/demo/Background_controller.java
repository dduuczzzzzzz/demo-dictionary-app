package com.example.demo;

import com.example.demo.api.Translator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import com.sun.speech.freetts.*;

public class Background_controller implements Initializable {

    @FXML
    private ListView<String> DictionaryList;

    @FXML
    private TextArea meaningArea;

    @FXML
    private Label def_label;

    @FXML
    private TextField searchField;



    private final DictionaryManagement dictManagement = new DictionaryManagement();
    List<String> searchResult = new ArrayList<>();
    Word currentWord = new Word();

    // the list of words
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            dictManagement.getWords().clear();
            dictManagement.loadFromFile();
            DictionaryList.getItems().clear();

            searchResult = dictManagement.searchWord("");
            for(String word: searchResult) {
                DictionaryList.getItems().add(word);
            }
            DictionaryList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                int index = DictionaryList.getSelectionModel().getSelectedIndex();
                if(index != -1) {
                    String wordTarget = DictionaryList.getItems().get(index);
                    currentWord.setWord_target(wordTarget);
                    def_label.setText(currentWord.getWord_target());
                    setCurrentWordExplain();
                    meaningArea.setText(currentWord.getWord_explain());
                }
            });

            searchField.textProperty().addListener((observableValue, oldValue, newValue) ->{
                DictionaryList.getItems().clear();
                searchResult.clear();
                searchResult.addAll(dictManagement.searchWord(newValue));
                for(String word: searchResult){
                    DictionaryList.getItems().add(word);
                }
            });
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentWordExplain (){
        int indexInWords = Collections.binarySearch(dictManagement.getWords(),currentWord);
        if (indexInWords != -1){
            currentWord.setWord_explain(dictManagement.getWords().get(indexInWords).getWord_explain());
        }
    }

    public void removeWord() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Remove the word: " + currentWord.getWord_target() +" ?");
        Optional<ButtonType> result =  alert.showAndWait();
        if(result.isPresent() && result.get() ==ButtonType.OK){
            dictManagement.removeWord(currentWord);
        }
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
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Word newWord = new Word(word.getText(), meaning.getText());
            if (Objects.equals(word.getText(), "")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Word field is empty");
                alert.showAndWait();
            } else if (Collections.binarySearch(dictManagement.getWords(), newWord) < 0) {
                dictManagement.insertSingleWord(newWord);
                dictManagement.sortWords();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Word added successfully");
                alert.showAndWait();
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
        if(currentWord == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No word selected");
            alert.showAndWait();
            return;
        }
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

        if(result.isPresent() && result.get() == ButtonType.OK){
            if(Objects.equals(word.getText(),"")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Word field is empty");
                alert.showAndWait();
            }
            else{
                int index = Collections.binarySearch(dictManagement.getWords(), currentWord);
                dictManagement.modifyWord(index,word.getText(),meaning.getText());
                dictManagement.sortWords();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Word modified");
                alert.showAndWait();
            }
        }
    }

    public void ggTranslate() throws IOException {
        String text = searchField.getText();
        if(!Objects.equals(text, "")){
            def_label.setText(text);
            meaningArea.setText(Translator.translateEtoV(text));
        }
    }

    public void speak(){
        System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        String text = def_label.getText();
        //voice.speak(input);
        if(!Objects.equals(text,"")){
            try{
                voice.speak(text);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
