package com.ziwookim.random_nickname_maker;

import com.ziwookim.random_nickname_maker.service.WordService;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NickNameMaker {

    private final Map<PartOfSpeech, List<Word>> wordsMap = new WordService().loadWords();
    private final Map<PartOfSpeech, Map<Integer, List<String>>> lengthWordsMap = new WordService().loadLengthWords();

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
  
    NickNameMaker() {}

    String callNickNameMaker(NumberOfPhrase numberOfPhrase, boolean isIncludeBlank, int maxLength) {
        if(maxLength == -1) maxLength = Integer.MAX_VALUE;

        setNickNameCandidateList(numberOfPhrase, maxLength);

        if(nickNameCandidateList.isEmpty()) {
            throw new IllegalArgumentException("닉네임 최대 글자수 값을 늘려 주세요.");
        }

        String nickName = nickNameCandidateList.get(randomIndex(nickNameCandidateList.size()));

        if(!isIncludeBlank) {
            nickName = nickName.replace(" ", "");
        }

        log.info("nickName: {}", nickName);
        return nickName;
    }

    public String callAdvancedNickNameMaker(NumberOfPhrase numberOfPhrase, boolean isIncludeBlank, int maxLength) {
        log.info("numberOfPhrase: {}, isIncludeBlank: {}, maxLength: {}", numberOfPhrase.ordinal(), isIncludeBlank, maxLength);
        if(maxLength == -1) maxLength = Integer.MAX_VALUE;

        int phrase = numberOfPhrase.ordinal();
        log.info("phrase value: {}", phrase);

        List<int[]> lengthCandidates = new ArrayList<>();
        setLengthCandidateList(phrase, maxLength, lengthCandidates);

        if(lengthCandidates.isEmpty()) {
            throw new IllegalArgumentException("닉네임 최대 글자수 값을 늘려 주세요.");
        }

        int[] randomLengthArray = lengthCandidates.get(randomIndex(lengthCandidates.size()));
        String nickName = "";

        if(numberOfPhrase.equals(NumberOfPhrase.PHRASE_2)) {
            String adjective = lengthWordsMap.get(PartOfSpeech.ADJECTIVE).get(randomLengthArray[0]).get(randomIndex( lengthWordsMap.get(PartOfSpeech.ADJECTIVE).get(randomLengthArray[0]).size()));
            String noun = lengthWordsMap.get(PartOfSpeech.NOUN).get(randomLengthArray[0]).get(randomIndex( lengthWordsMap.get(PartOfSpeech.NOUN).get(randomLengthArray[1]).size()));

            nickName = adjective + (isIncludeBlank ? " " : "") + noun;
        } else if(numberOfPhrase.equals(NumberOfPhrase.PHRASE_3)) {
            String adverb = lengthWordsMap.get(PartOfSpeech.ADVERB).get(randomLengthArray[0]).get(randomIndex( lengthWordsMap.get(PartOfSpeech.ADVERB).get(randomLengthArray[0]).size()));
            String adjective = lengthWordsMap.get(PartOfSpeech.ADJECTIVE).get(randomLengthArray[0]).get(randomIndex( lengthWordsMap.get(PartOfSpeech.ADJECTIVE).get(randomLengthArray[1]).size()));
            String noun = lengthWordsMap.get(PartOfSpeech.ADJECTIVE).get(randomLengthArray[0]).get(randomIndex( lengthWordsMap.get(PartOfSpeech.NOUN).get(randomLengthArray[2]).size()));

            nickName = adverb + (isIncludeBlank ? " " : "") + adjective + (isIncludeBlank ? " " : "") + noun;
        } else { // numberOfPhrase.equals(NumberOfPhrase.PHRASE_1)
            String noun = lengthWordsMap.get(PartOfSpeech.NOUN).get(randomLengthArray[0]).get(randomIndex( lengthWordsMap.get(PartOfSpeech.NOUN).get(randomLengthArray[0]).size()));

            nickName = noun;
        }

        log.info("nickName: {}", nickName);
        return nickName;
    }

    private int randomIndex(int size) {
        return secureRandom.nextInt(size);
    }

    private void setNickNameCandidateList(NumberOfPhrase numberOfPhrase, int maxLength) {
        switch (numberOfPhrase) {
            case NumberOfPhrase
                    .PHRASE_2:
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

    private void setLengthCandidateList(int phrase, int maxLength, List<int[]> lengthCandidates) {
        List<Integer> adverbLengthList = lengthWordsMap.get(PartOfSpeech.ADVERB).keySet().stream().sorted().toList();
        List<Integer> adjectiveLengthList = lengthWordsMap.get(PartOfSpeech.ADJECTIVE).keySet().stream().sorted().toList();
        List<Integer> nounLengthList = lengthWordsMap.get(PartOfSpeech.NOUN).keySet().stream().sorted().toList();
        log.info("dictionary lengthList.. adverbLengthList: {}, adjectiveLengthList: {}, nounLengthList: {}", adverbLengthList.size(), adjectiveLengthList.size(), nounLengthList.size());
        switch (phrase) {
            case 2:
                for(int adjectiveLength : adjectiveLengthList) {
                    for(int nounLength : nounLengthList) {
                        if(adjectiveLength + nounLength <= maxLength) {
                            lengthCandidates.add(new int[] {adjectiveLength, nounLength});
                            log.info("adjectiveLength: {}, nounLength: {}", adjectiveLength, nounLength);
                        }
                    }
                }
                break;
            case 1:
                for(int nounLength : nounLengthList) {
                    if(nounLength <= maxLength) {
                        lengthCandidates.add(new int[] {nounLength});
                        log.info("nounLength: {}", nounLength);
                    }
                }
                break;
            case 3:
                for(int adverbLength : adverbLengthList) {
                    for(int adjectiveLength : adjectiveLengthList) {
                        for(int nounLength : nounLengthList) {
                            if(adverbLength + adjectiveLength + nounLength <= maxLength) {
                                lengthCandidates.add(new int[] {adverbLength, adjectiveLength, nounLength});
                                log.info("adverbLength: {}, adjectiveLength: {}, nounLength: {}", adverbLength, adjectiveLength, nounLength);
                            }
                        }
                    }
                }
                break;
        }
    }

}
