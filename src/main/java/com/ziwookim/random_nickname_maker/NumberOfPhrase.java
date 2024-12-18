package com.ziwookim.random_nickname_maker;

public enum NumberOfPhrase {

    PHRASE_1(1),
    PHRASE_2(2),
    PHRASE_3(3)
    ;

    private final Integer numberOfPhrase;

    NumberOfPhrase(Integer numberOfPhrase) {
        this.numberOfPhrase = numberOfPhrase;
    }

    public Integer getNumberOfPhrase() {
        return numberOfPhrase;
    }
}
