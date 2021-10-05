package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline {
    private final DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private final Dictionary newDictionary = dictionaryManagement.getDictionary();
    private final List<Word> newWords = newDictionary.getWords();
    private List<Word> searchList = new ArrayList<>();

    private void showAllWords() {
        System.out.format("%-5s %-30s %-30s \n", "No", "| English", "| Vietnamese");
        for (int i = 0; i < newWords.size(); i++) {
            System.out.format("%-5s %-30s %-30s \n", (i + 1), "| " + newWords.get(i).getWord_target(), "| " + newWords.get(i).getWord_explain());
        }
    }

    /*
        public static void dictionaryBasic(){
            dictionaryManagement.insertFromCommandline();
            showAllWords();
        }
    */
    public void dictionaryAdvanced() {
        dictionaryManagement.insertFromFile();
        //showAllWords();
        //dictionaryManagement.dictionaryLookup();
    }

    public void dictionarySeacher(String input) {
        for (Word words : dictionaryManagement.getDictionary().getWords()) {
            if (words.getWord_target().contains(input)) {
                searchList.add(words);
            }
        }
    }

    public void clear_SearchList(){
        searchList.clear();
    }
    public List<Word> getSearchList(){
        return searchList;
    }

    public void printSearchList(){
        for(Word words : searchList){
            System.out.println(words.getWord_target() + "   " + words.getWord_explain());
        }
    }

    public DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }

}
