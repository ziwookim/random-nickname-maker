package com.ziwookim.random_nickname_maker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class Word {
//    private String partOfSpeech;
    private String word;
    private int length;

    public Word(String partOfSpeech, String word) {
//        this.partOfSpeech = partOfSpeech;
        this.word = word;
        this.length = word.length();
    }
}
