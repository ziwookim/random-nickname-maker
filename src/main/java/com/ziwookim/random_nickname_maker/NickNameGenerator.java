package com.ziwookim.random_nickname_maker;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NickNameGenerator {

    // TODO: 12/11/24 maxLength 처리 
    private static Map<PartOfSpeech, Map<Integer, List<Word>>> searchWords = new HashMap<>();
    private static Map<PartOfSpeech, List<Word>> words = new HashMap<>();
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String getNickName(RandomNickNameMaker randomNickNameMaker) {
        NumberOfPhrase numberOfPhrase = randomNickNameMaker.getNumberOfPhrase();
        boolean isIncludeBlank = randomNickNameMaker.isIncludedBlank();
        int maxLength = randomNickNameMaker.getMaxLength();

        StringBuilder stringBuilder = new StringBuilder();

        if(numberOfPhrase.equals(NumberOfPhrase.PHRASE_3)) {
            stringBuilder.append(pickRandomWord(PartOfSpeech.ADVERB));
            if(isIncludeBlank) {
                stringBuilder.append(" ");
            }
        }

        if(numberOfPhrase.equals(NumberOfPhrase.PHRASE_2)) {
            stringBuilder.append(pickRandomWord(PartOfSpeech.ADJECTIVE));
            if(isIncludeBlank) {
                stringBuilder.append(" ");
            }
        }

        stringBuilder.append(pickRandomWord(PartOfSpeech.NOUN));

        return stringBuilder.toString();
    }

    public static String pickRandomWord(PartOfSpeech partOfSpeech) {
        return words.get(partOfSpeech).get(randomIndex(partOfSpeech)).getWord();
    }

    public static String pickRandomWord(PartOfSpeech partOfSpeech, int maxLength) {
        return words.get(partOfSpeech).get(randomIndex(partOfSpeech)).getWord();
    }

    public static int randomIndex(PartOfSpeech partOfSpeech) {
        int maxIndex = words.get(partOfSpeech).size();
        return secureRandom.nextInt(maxIndex);
    }

    public static int randomIndex(PartOfSpeech partOfSpeech, int maxLength) {
        int maxIndex = words.get(partOfSpeech).size();
        return secureRandom.nextInt(maxIndex);
    }
}
