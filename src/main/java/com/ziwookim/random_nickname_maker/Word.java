package com.ziwookim.random_nickname_maker;

import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class Word {
    private String word;
    private int length;

    public Word(String partOfSpeech, String word) {
        this.word = word;
        this.length = word.length();
    }
}
