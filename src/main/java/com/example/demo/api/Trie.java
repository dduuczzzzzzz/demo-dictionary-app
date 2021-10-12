package com.example.demo.api;

import java.util.*;
import com.example.demo.api.TrieNode;
public class Trie {

    TrieNode root;
    public Trie(){
        root = new TrieNode();
    }

    public Trie(List<String> words) {
        root = new TrieNode();
        for (String word : words)
            insertWord(word);
    }

    public void insertWord(String word){
        root.insert(word);
    }

    public void deleteWord(String word){
        if (word == null || word.isEmpty()){
            return;
        }
        TrieNode curr = root;
        for(int i=0;i<word.length();i++){
            curr=curr.children.get(word.charAt(i));
        }
        curr.isWord = false;
    }

    public boolean find(String prefix, boolean exact) {
        TrieNode lastNode = root;
        for (char c : prefix.toCharArray()) {
            lastNode = lastNode.children.get(c);
            if (lastNode == null)
                return false;
        }
        return !exact || lastNode.isWord;
    }

    public boolean find(String prefix) {
        return find(prefix, false);
    }

    private void suggestHelper(TrieNode root, List<String> list, StringBuilder curr) {
        if (root.isWord) {
            list.add(curr.toString());
        }

        if (root.children == null || root.children.isEmpty())
            return;

        for (TrieNode child : root.children.values()) {
            suggestHelper(child, list, curr.append(child.c));
            curr.setLength(curr.length() - 1);
        }
    }

    public List<String> suggest(String prefix) {
        List<String> list = new ArrayList<>();
        TrieNode lastNode = root;
        StringBuilder curr = new StringBuilder();
        for (char c : prefix.toCharArray()) {
            lastNode = lastNode.children.get(c);
            if (lastNode == null)
                return list;
            curr.append(c);
        }
        suggestHelper(lastNode, list, curr);
        return list;
    }
}