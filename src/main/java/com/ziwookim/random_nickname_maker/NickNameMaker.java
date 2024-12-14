package com.ziwookim.random_nickname_maker;

import com.ziwookim.random_nickname_maker.service.WordService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NickNameMaker {

    private final Map<PartOfSpeech, List<Word>> wordsMap = new WordService().loadWords();

    private final List<String> nickNameCandidateList = new ArrayList<>();

    private final int ADVERB_MIN_LENGTH = wordsMap.get(PartOfSpeech.ADVERB).stream()
            .mapToInt(Word::getLength)
            .min()
            .orElse(0);

    private final int ADJECTIVE_MIN_LENGTH = wordsMap.get(PartOfSpeech.ADJECTIVE).stream()
            .mapToInt(Word::getLength)
            .min()
            .orElse(0);

    private final int NOUN_MIN_LENGTH = wordsMap.get(PartOfSpeech.NOUN).stream()
            .mapToInt(Word::getLength)
            .min()
            .orElse(0);


    private final SecureRandom secureRandom = new SecureRandom();

    public String callNickNameMaker(NumberOfPhrase numberOfPhrase, boolean isIncludeBlank, int maxLength) {
        if(maxLength == -1) maxLength = Integer.MAX_VALUE;

        System.out.println("maxLength: " +  maxLength);

        setNickNameCandidateList(numberOfPhrase, maxLength);

        if(nickNameCandidateList.isEmpty()) {
            throw new IllegalArgumentException("닉네임 최대 글자수 값을 늘려 주세요.");
        }

        String nickName = nickNameCandidateList.get(randomIndex(nickNameCandidateList.size()));

        if(!isIncludeBlank) {
            nickName = nickName.replace(" ", "");
        }
        System.out.println(nickName);

        return nickName;
    }

    private int randomIndex(int size) {
        return secureRandom.nextInt(size);
    }

    private void setNickNameCandidateList(NumberOfPhrase numberOfPhrase, int maxLength) {
        switch (numberOfPhrase) {
            case NumberOfPhrase.PHRASE_2:
                if(maxLength < ADJECTIVE_MIN_LENGTH + NOUN_MIN_LENGTH) return;

                for (Word adjective : wordsMap.get(PartOfSpeech.ADJECTIVE)) {
                    for (Word noun : wordsMap.get(PartOfSpeech.NOUN)) {
                        if (adjective.getLength() + noun.getLength() <= maxLength) {
                            nickNameCandidateList.add(adjective.getWord() + " " + noun.getWord());
                        }
                    }
                }
                break;
            case NumberOfPhrase.PHRASE_3:
                if(maxLength < ADVERB_MIN_LENGTH + ADJECTIVE_MIN_LENGTH + NOUN_MIN_LENGTH) return;

                for (Word adverb : wordsMap.get(PartOfSpeech.ADVERB)) {
                    for (Word adjective : wordsMap.get(PartOfSpeech.ADJECTIVE)) {
                        for (Word noun : wordsMap.get(PartOfSpeech.NOUN)) {
                            if (adverb.getLength() + adjective.getLength() + noun.getLength() <= maxLength) {
                                nickNameCandidateList.add(adverb.getWord() + " " + adjective.getWord() + " " + noun.getWord());
                            }
                        }
                    }
                }
                break;
            case NumberOfPhrase.PHRASE_1:
                if(maxLength < NOUN_MIN_LENGTH) return;

                wordsMap.get(PartOfSpeech.NOUN).stream().filter(word -> word.getLength() <= maxLength).toList()
                        .forEach(word -> nickNameCandidateList.add(word.getWord()));
                break;
        }
    }

}
