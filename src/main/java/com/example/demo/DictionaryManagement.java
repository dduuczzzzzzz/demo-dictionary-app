package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class DictionaryManagement {
    private final Dictionary dictionary = new Dictionary();

    public void insertSingleWord(Word newWord) {
        dictionary.insertWord(newWord);
        sortWords();
    }

    public void loadFromFile() throws IOException {
        Path path = Paths.get("src/main/resources/com/example/demo/dictData.txt");
        List<String> dataList = Files.readAllLines(path);
        Word newWord = new Word();

        for(int i=1;i<=dataList.size();i++){
            if(i == dataList.size()){
                dictionary.insertWord(newWord);
                break;
            }
            if (Objects.equals(dataList.get(i),"\n") || Objects.equals(dataList.get(i),"" )) continue;
            if(Objects.equals(dataList.get(i),"$")) continue;
            if(Objects.equals(dataList.get(i - 1), "$")){
                if(i>1) {
                    dictionary.insertWord(newWord);
                }
                newWord = new Word();
                newWord.setWord_target(dataList.get(i));
            }
            else{
                newWord.add_to_explain(dataList.get(i));
            }
        }
        sortWords();
    }

    public void removeWord(Word removedWord) {
        dictionary.removeWord(removedWord);
    }

    public void modifyWord(Word oldWord, String word_target, String word_explain) {
        removeWord(oldWord);
        Word word = new Word(word_target,word_explain);
        insertSingleWord(word);
    }

    //Backup old data and save current data
    public void exportDataToFile() throws IOException {
        Path data = Paths.get("src/main/resources/com/example/demo/dictData.txt");
        Path backup = Paths.get("src/main/resources/com/example/demo/dictDataBackup.txt");
        Files.delete(backup);
        Files.copy(data,backup);

        PrintWriter pw = new PrintWriter("src/main/resources/com/example/demo/dictData.txt");
        for(Word curr : getWords()){
            pw.println("$");
            pw.println(curr.getWord_target());
            pw.println(curr.getWord_explain());
            pw.flush();
        }
    }

    public void sortWords() {
        dictionary.sortWords();
    }

    public List<String> searchWord(String prefix) {
        return dictionary.wordSuggest(prefix);
    }

    public List<Word> getWords() {
        return dictionary.getWords();
    }
}
