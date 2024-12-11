package com.ziwookim.random_nickname_maker.service;

import com.ziwookim.random_nickname_maker.PartOfSpeech;
import com.ziwookim.random_nickname_maker.Word;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WordService {

//    private List<Word> words = new ArrayList<>();
    private Map<PartOfSpeech, List<Word>> wordsMap = new HashMap<>();

    public void loadWords() {
        String filePath = Paths.get("./nickname_dictionary.csv").toAbsolutePath().toString();

        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for(CSVRecord record : csvParser) {
                String partOfSpeech = record.get("partOfSpeech");
                String wordContent = record.get("word");

                Word word = new Word(partOfSpeech, wordContent);
//                words.add(word);

                if(!wordsMap.containsKey(PartOfSpeech.valueOf(partOfSpeech))) {
                    wordsMap.put(PartOfSpeech.valueOf(partOfSpeech), new ArrayList<>());
                }
                wordsMap.get(PartOfSpeech.valueOf(partOfSpeech)).add(word);

            }
        } catch (IOException e) {
            log.info("IOException is occurred. message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int getMaxLength(PartOfSpeech partOfSpeech) {
        return wordsMap.get(partOfSpeech).stream()
                .mapToInt(Word::getLength)
                .max()
                .orElse(0);
    }

    public int getMinLength(PartOfSpeech partOfSpeech) {
        return wordsMap.get(partOfSpeech).stream()
                .mapToInt(Word::getLength)
                .min()
                .orElse(0);
    }

    public List<Word> getWordsShorterThan(PartOfSpeech partOfSpeech, int maxLength) {
        return wordsMap.get(partOfSpeech).stream()
                .filter(word -> word.getLength() <= maxLength)
                .collect(Collectors.toList());
    }
}
