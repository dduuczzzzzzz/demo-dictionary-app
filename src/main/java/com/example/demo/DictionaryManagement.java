package com.example.demo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;

public class DictionaryManagement {
    private final Dictionary dictionary = new Dictionary();

    public void insertSingleWord(Word newWord) {
        dictionary.insertWord(newWord);
        sortWords();
    }

    public void loadFromFile() {
        try {
            //words = new Vector<>();
            Path path = Paths.get("src/main/resources/com/example/demo/advancedDict.txt");
            List<String> dataList = Files.readAllLines(path);
            ListIterator<String> itr = dataList.listIterator();

            Word word;
            while (itr.hasNext()) {
                String p = itr.next();

                if (p.startsWith("@")) {
                    word = new Word();
                    String[] part = p.split("/", 2);

                    String s2 = part[0].substring(1).trim();
                    if (s2.startsWith("'") || s2.startsWith("-") || s2.startsWith("(")) {
                        s2 = s2.substring(1);
                    }
                    word.setWord_target(s2);

                    if (part.length < 2) {
                        word.add_to_explain("");
                    } else
                        word.add_to_explain("/" + part[1] + "\n");
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
            sortWords();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeWord(Word removedWord) {
        dictionary.removeWord(removedWord);
    }

    public void modifyWord(int index, String word_target, String word_explain) {
        dictionary.getWords().get(index).setWord_target(word_target);
        dictionary.getWords().get(index).setWord_explain(word_explain);
    }

    public void sortWords() {
        dictionary.sortWords();
    }

    public List<String> searchWord(String prefix) {
        return dictionary.wordSuggest(prefix);
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public List<Word> getWords() {
        return getDictionary().getWords();
    }
}
