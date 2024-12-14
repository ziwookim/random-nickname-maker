package com.ziwookim.random_nickname_maker;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RandomNickNameMaker {

    private final boolean DEFAULT_IS_INCLUDE_BLANK = true;
    private final Integer DEFAULT_MAX_LENGTH = -1;
    private final NumberOfPhrase DEFAULT_NUMBER_OF_PHRASE = NumberOfPhrase.PHRASE_2;

    private boolean isIncludedBlank = DEFAULT_IS_INCLUDE_BLANK;
    private NumberOfPhrase numberOfPhrase = DEFAULT_NUMBER_OF_PHRASE;
    private Integer maxLength = DEFAULT_MAX_LENGTH;

    public static RandomNickNameMaker builder() {
        return new RandomNickNameMaker();
    }

    public RandomNickNameMaker maxLength(int  maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public RandomNickNameMaker numberOfPhrase(NumberOfPhrase numberOfPhrase) {
        this.numberOfPhrase = numberOfPhrase;
        return this;
    }

    public RandomNickNameMaker isIncludedBlank(boolean isIncludedBlank) {
        this.isIncludedBlank = isIncludedBlank;
        return this;
    }

    public String callNickNameMaker() {
        return new NickNameMaker().callNickNameMaker(this.numberOfPhrase, this.isIncludedBlank, this.maxLength);
    }
}
