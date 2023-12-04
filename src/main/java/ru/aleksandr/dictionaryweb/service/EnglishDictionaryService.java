package ru.aleksandr.dictionaryweb.service;

import org.springframework.stereotype.Service;
import ru.aleksandr.dictionaryweb.dao.EnglishDictionaryDAO;
import ru.aleksandr.dictionaryweb.entity.EnglishTranslateWord;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EnglishDictionaryService {

    private final EnglishDictionaryDAO englishDictionaryDAO;

    public EnglishDictionaryService(EnglishDictionaryDAO englishDictionaryDAO) {
        this.englishDictionaryDAO = englishDictionaryDAO;
    }

    public List<String> showAll() {
        List<String> result = new ArrayList<>();
        List<EnglishWord> arr = englishDictionaryDAO.getAll();

        for (EnglishWord ew : arr) {
            StringBuffer translations = new StringBuffer();

            if (ew.getEnglishTranslateWords().isEmpty()) {
                translations.append("there is no translation yet");
            } else {

                translations.append(ew.getEnglishTranslateWords().get(0).getTranslation());
                for (int i = 1; i < ew.getEnglishTranslateWords().size(); i++) {
                    translations.append(", ");
                    translations.append(ew.getEnglishTranslateWords().get(i).getTranslation());
                }
            }
            result.add(ew.getWord() + " - " + translations);
        }
        return result;
    }

    public List<EnglishWord> showAll1() {
        return englishDictionaryDAO.getAll();
    }

    public EnglishWord showByWord(String word) {
        EnglishWord ew = englishDictionaryDAO.getByKey(word);
        return ew;
    }

    public void save(String word) {
        String[] arr = word.split(" ");
        EnglishWord englishWord = new EnglishWord();
        EnglishTranslateWord englishTranslateWord = new EnglishTranslateWord();

        englishTranslateWord.setTranslation(arr[1]);
        englishTranslateWord.setEnglishWord(englishWord);

        englishWord.setWord(Integer.valueOf(arr[0]));
        englishWord.setEnglishTranslateWords(Collections.singletonList(englishTranslateWord));

        englishDictionaryDAO.save(englishWord);
    }

    public void update(String word) {
        String[] arr = word.split(" ");

        EnglishWord englishWord = new EnglishWord();
        EnglishTranslateWord englishTranslateWord = new EnglishTranslateWord();

        englishTranslateWord.setEnglishWord(englishWord);
        englishTranslateWord.setTranslation(arr[1]);

        englishWord.setWord(Integer.valueOf(arr[0]));
        englishWord.setEnglishTranslateWords(Collections.singletonList(englishTranslateWord));
        englishDictionaryDAO.update(englishWord);
    }
}