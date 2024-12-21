package com.ziwookim.random_nickname_maker.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.ziwookim.random_nickname_maker.PartOfSpeech;
import com.ziwookim.random_nickname_maker.Word;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordService {

    private final static Map<PartOfSpeech, List<Word>> wordsMap = new HashMap<>();
    private final static Map<PartOfSpeech, Map<Integer, List<String>>> lengthWordsMap = new HashMap<>();

    private WordService() {
    }

    private static final class InstanceHolder {
        private static final WordService instance = new WordService();
    }

    public static WordService getInstance() {
        if(wordsMap.isEmpty()) {
            InstanceHolder.instance.loadWords();
        }
        return InstanceHolder.instance;
    }

    public Map<PartOfSpeech, List<Word>> getWordsMap() {
        return wordsMap;
    }

    private void loadWords() {

        File file = new File("./nickname_dictionary.csv").getAbsoluteFile();

        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader(file));
            String[] record;

            while((record = csvReader.readNext()) != null) {
                String partOfSpeech = record[0];
                String wordContent = record[1];

                Word word = new Word(partOfSpeech, wordContent);

                if(!wordsMap.containsKey(PartOfSpeech.valueOfPartOfSpeech(partOfSpeech))) {
                    wordsMap.put(PartOfSpeech.valueOfPartOfSpeech(partOfSpeech), new ArrayList<>());
                }
                wordsMap.get(PartOfSpeech.valueOfPartOfSpeech(partOfSpeech)).add(word);

            }
            csvReader.close();
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException("닉네임 리스트 파일을 읽어오는 도중 문제가 발생했습니다.");
        }

    }
}
