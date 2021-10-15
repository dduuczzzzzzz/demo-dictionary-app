package com.example.demo;

import com.example.demo.utils.Trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
    private final List<Word> words = new ArrayList<>();
    private final Trie wordsTrie = new Trie();

    public void insertWord(Word newWord) {
        words.add(newWord);
        wordsTrie.insertWord(newWord.getWord_target());
    }

    public void removeWord(Word removedWord) {
        int index = Collections.binarySearch(words,removedWord);
        words.remove(index);
        wordsTrie.deleteWord(removedWord.getWord_target());
    }

    public void sortWords() {
        Collections.sort(words);
    }

    public List<Word> getWords() {
        return words;
    }

    public List<String> wordSuggest(String s){
        return wordsTrie.suggest(s);
    }
}
