package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
            file = new File("src/main/resources/com/example/demo/dictionariess.txt");
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

    public void loadFromFile() {
        try {
            //words = new Vector<>();
            Path path = Paths.get("src/main/resources/com/example/demo/advancedDict.txt");
            List<String> dataList = Files.readAllLines(path);
            ListIterator<String> itr = dataList.listIterator();
            //code to read data from file to Vector
            Word word = new Word();
            int count = 0;
            while (itr.hasNext()) {
                String p = itr.next();

                if (p.startsWith("@")) {
                    count++;
                    word = new Word();
                    String[] part = p.split("/", 2);

                    String s2 = part[0].substring(1).trim();
                    if (s2.startsWith("'") || s2.startsWith("-") || s2.startsWith("(")) {
                        s2 = s2.substring(1, s2.length());
                    }
                    word.setWord_target(s2);

                    /*if (part.length < 2) {
                        word.setPhonetics("");
                    } else
                        word.setPhonetics("/" + part[1]);*/
                    while (itr.hasNext()) {
                        String p1 = itr.next();
                        if (!p1.startsWith("@")) {
                            //word.setWord_explain(p1);
                            word.add_to_explain(p1);
                            word.add_to_explain("\n");
                        } else {
                            dictionary.insertWord(word);
                            itr.previous();
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
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
