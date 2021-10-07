package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
    private final List<Word> words = new ArrayList<>();

    public void insertWord(Word newWord) {
        words.add(newWord);
    }

    public void removeWord(String removedWord) {
        words.removeIf(i -> i.getWord_target().equals(removedWord));
    }

    public void sortWords() {
        Collections.sort(words);
    }

    public List<Word> getWords() {
        return words;
    }
}
