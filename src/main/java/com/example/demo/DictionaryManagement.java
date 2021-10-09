package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    private final Dictionary dictionary = new Dictionary();
    private final List<Word> searchList = new ArrayList<>();

    public void insertSingleWord(Word newWord) {
        dictionary.insertWord(newWord);
    }

    public void insertFromFile() {
        try {
            File file;
            file = new File("src/main/java/com/example/demo/dictionariess.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] s = sc.nextLine().split("\t");
                String English = s[0];
                String translate = s[1];
                Word newWord = new Word(English, translate);
                dictionary.insertWord(newWord);
            }
            dictionary.sortWords();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeWord(Word removedWord) {
        dictionary.removeWord(removedWord);
    }

    public void dictionarySeacher(String input) {
        for (Word words : dictionary.getWords()) {
            if (words.getWord_target().contains(input)) {
                searchList.add(words);
            }
        }
    }

    public void clear_SearchList() {
        searchList.clear();
    }

    public List<Word> getSearchList() {
        return searchList;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
}
