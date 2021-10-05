package com.example.demo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    private final Dictionary dictionary = new Dictionary();

    private int binarySearch(List<Word> words, int l, int r, String wordToFind) {
        if (l <= r) {
            int mid = l + (r - 1) / 2;
            if (words.get(mid).getWord_target().equals(wordToFind)) {
                return mid;
            }
            if (words.get(mid).getWord_target().compareTo(wordToFind) > 0) {
                return binarySearch(words, l, mid - 1, wordToFind);
            }
            return binarySearch(words, mid + 1, r, wordToFind);
        }
        return -1;
    }

    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("So luong tu can them: ");
        int n = scanner.nextInt();
        System.out.println("Nhap lan luot tu va nghia:");
        for (int i = 0; i < n; i++) {
            Scanner input = new Scanner(System.in);
            String English = input.nextLine();
            String translate = input.nextLine();
            Word newWord = new Word(English, translate);
            dictionary.insertWord(newWord);
        }
        dictionary.sortWords();
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

    public void removeWord() {
        String removedWord;
        Scanner input = new Scanner(System.in);
        System.out.println("Nhap tu ban muon xoa: ");
        removedWord = input.nextLine();
        dictionary.removeWord(removedWord);
    }

    public void changeWord(){
        int index;
        System.out.println("Nhap tu ban muon sua: ");
        Scanner nc = new Scanner(System.in);
        String wordtoChange = nc.nextLine();
        index = binarySearch(dictionary.getWords(), 0, dictionary.getWords().size() - 1, wordtoChange);
        if (index == -1) {
            System.out.println("Khong co tu ban muon sua!");
        }
        else {
            System.out.println("Nhap y nghia ban muon sua: ");
            Scanner scanner = new Scanner(System.in);
            String newChange = scanner.nextLine();
            dictionary.getWords().get(index).setWord_explain(newChange);
        }
    }

    public void dictionaryLookup() {
        int index;
        System.out.println("Nhap tu ban muon tim: ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        index = binarySearch(dictionary.getWords(), 0, dictionary.getWords().size() - 1, s);
        if (index == -1) {
            System.out.println("Khong co tu ban can tim");
        }
        else{
            System.out.println(dictionary.getWords().get(index).getWord_explain());
        }
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
}
