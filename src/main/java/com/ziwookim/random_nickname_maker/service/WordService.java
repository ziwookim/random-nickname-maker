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

    private final Map<PartOfSpeech, List<Word>> wordsMap = new HashMap<>();
    private final Map<PartOfSpeech, Map<Integer, List<String>>> lengthWordsMap = new HashMap<>();

    public Map<PartOfSpeech, List<Word>> loadWords() {

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

        return wordsMap;
    }

    public Map<PartOfSpeech, Map<Integer, List<String>>> loadLengthWords() {

        File file = new File("./nickname_dictionary.csv").getAbsoluteFile();

        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader(file));
            String[] record;

            while((record = csvReader.readNext()) != null) {
                String partOfSpeech = record[0];
                String wordContent = record[1];

                Word word = new Word(partOfSpeech, wordContent);

                // 성능 개선을 위한 Map<품사, Map<문자열 길이, List<단어>>> 추가
                if(!lengthWordsMap.containsKey(PartOfSpeech.valueOfPartOfSpeech(partOfSpeech))) {
                    lengthWordsMap.put(PartOfSpeech.valueOfPartOfSpeech(partOfSpeech), new HashMap<>());
                } else {
                    if(!lengthWordsMap.get(PartOfSpeech.valueOfPartOfSpeech(partOfSpeech)).containsKey(word.getLength())) {
                        lengthWordsMap.get(PartOfSpeech.valueOfPartOfSpeech(partOfSpeech)).put(word.getLength(), new ArrayList<>());
                    }
                    lengthWordsMap.get(PartOfSpeech.valueOfPartOfSpeech(partOfSpeech)).get(word.getLength()).add(wordContent);
                }

            }
            csvReader.close();
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException("닉네임 리스트 파일을 읽어오는 도중 문제가 발생했습니다.");
        }

        return lengthWordsMap;
    }
}
