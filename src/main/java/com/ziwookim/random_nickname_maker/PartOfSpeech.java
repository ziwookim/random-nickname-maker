package com.ziwookim.random_nickname_maker;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PartOfSpeech {

    NOUN("noun"),
    ADJECTIVE("adjective"),
    ADVERB("adverb")
    ;

    private final String partOfSpeech;
}
