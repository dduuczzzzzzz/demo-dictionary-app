package com.example.demo;

public class Word implements Comparable<Word>{
    private String word_target;
    private String word_explain;

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getWord_target() {
        return word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public Word(String English, String translate) {
        this.word_target = English;
        this.word_explain = translate;
    }

    @Override
    public int compareTo(Word other){
        return this.word_target.compareTo(other.getWord_target());
    }
}
