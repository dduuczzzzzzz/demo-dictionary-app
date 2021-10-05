package com.example.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Dictionary {
    private final List<Word> words = new ArrayList<>();

    Comparator<Word> wordComparator = Comparator.comparing(Word::getWord_target);

    public void insertWord(Word newWord) {
        words.add(newWord);
    }

    public void removeWord(String removedWord) {
        words.removeIf(i -> i.getWord_target().equals(removedWord));
    }

    public void sortWords() {
        words.sort(wordComparator);
    }

    public List<Word> getWords() {
        return words;
    }
}
