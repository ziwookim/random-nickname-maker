package com.ziwookim.random_nickname_maker.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.ziwookim.random_nickname_maker.PartOfSpeech;
import com.ziwookim.random_nickname_maker.Word;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WordService {

    private final Map<PartOfSpeech, List<Word>> wordsMap = new HashMap<>();

    public Map<PartOfSpeech, List<Word>> loadWords() {
//        Arrays.stream(PartOfSpeech.values()).forEach(partOfSpeech -> wordsMap.put(partOfSpeech, new ArrayList<>()));

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
            throw new RuntimeException(e);
        }

        return wordsMap;
    }
}
