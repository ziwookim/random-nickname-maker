package com.ziwookim.random_nickname_maker;

public class Word {
    private String word;
    private int length;

    public Word(String partOfSpeech, String word) {
        this.word = word;
        this.length = word.length();
    }

    public String getWord() {
        return word;
    }

    public int getLength() {
        return length;
    }
}
