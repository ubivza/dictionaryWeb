package ru.aleksandr.dictionaryweb.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<EnglishWord> showAll() {
        return englishDictionaryDAO.getAll();
    }

    public void saveString(String word) {
        String[] arr = word.split(" ");

        EnglishWord englishWord = new EnglishWord();
        englishWord.setWord(Integer.valueOf(arr[0]));

        //поработать с множественным переводом
        if (arr.length == 2) {
            EnglishTranslateWord englishTranslateWord = new EnglishTranslateWord();

            englishTranslateWord.setTranslation(arr[1]);
            englishTranslateWord.setEnglishWord(englishWord);
            englishWord.setEnglishTranslateWords(Collections.singletonList(englishTranslateWord));
        }

        englishDictionaryDAO.save(englishWord);
    }

    public EnglishWord showByKey(String key) {
        EnglishWord result = englishDictionaryDAO.getByKey(key);
        return result;
    }

    public List<EnglishWord> showByValue(String value) {
        return englishDictionaryDAO.getByValue(value);
    }

    public void deleteByKey(String deleteWord) {
        englishDictionaryDAO.deleteByKey(deleteWord);
    }
}