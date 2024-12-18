package com.ziwookim.random_nickname_maker;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum PartOfSpeech {

    NOUN("noun"),
    ADJECTIVE("adjective"),
    ADVERB("adverb")
    ;

    private final String partOfSpeech;

    public String partOfSpeech() {
        return partOfSpeech;
    }

    private static final Map<String, PartOfSpeech> partOfSpeechValueMap =
            Stream.of(values()).collect(Collectors.toMap(PartOfSpeech::partOfSpeech, Function.identity()));

    public static PartOfSpeech valueOfPartOfSpeech(String partOfSpeech) {
        return partOfSpeechValueMap.get(partOfSpeech);
    }

}
