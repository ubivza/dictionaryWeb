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

    public List<EnglishWord> showAll() {
        return englishDictionaryDAO.getAll();
    }

    public void saveString(String word) {
        String[] arr = word.split(" ", 2);
        String[] arrWords = new String[0];
        if (arr[1].contains(", ")) {
            arrWords = arr[1].split(", ");
        }

        EnglishWord englishWord = new EnglishWord();
        englishWord.setWord(Integer.valueOf(arr[0]));

        if (arr.length == 2 && !arr[1].isEmpty()) {
            EnglishTranslateWord englishTranslateWord = new EnglishTranslateWord();

            englishTranslateWord.setTranslation(arr[1]);
            englishTranslateWord.setEnglishWord(englishWord);
            List<EnglishTranslateWord> list = new ArrayList<>();
            list.add(englishTranslateWord);
            englishWord.setEnglishTranslateWords(list);
        }

        if (arrWords.length != 0) {
            List<EnglishTranslateWord> wordList = new ArrayList<>();
            for (int i = 0; i < arrWords.length; i++) {
                EnglishTranslateWord englishTranslateWord = new EnglishTranslateWord();

                englishTranslateWord.setTranslation(arrWords[i]);
                englishTranslateWord.setEnglishWord(englishWord);
                wordList.add(englishTranslateWord);
            }

            englishWord.setEnglishTranslateWords(wordList);
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

    public void updateById(Long id, String word) {
        EnglishWord englishWord = englishDictionaryDAO.getById(id);

        String[] arr = word.split(" ", 2);
        String[] arrWords = new String[0];
        if (arr[1].contains(", ")) {
            arrWords = arr[1].split(", ");
        }

        englishWord.setWord(Integer.valueOf(arr[0]));

        if (arr.length == 2 && !arr[1].isEmpty()) {
            EnglishTranslateWord englishTranslateWord = new EnglishTranslateWord();

            englishTranslateWord.setTranslation(arr[1]);
            englishTranslateWord.setEnglishWord(englishWord);
            List<EnglishTranslateWord> list = new ArrayList<>();
            list.add(englishTranslateWord);
            englishWord.setEnglishTranslateWords(list);
        }

        if (arrWords.length != 0) {
            List<EnglishTranslateWord> wordList = new ArrayList<>();
            for (int i = 0; i < arrWords.length; i++) {
                EnglishTranslateWord englishTranslateWord = new EnglishTranslateWord();

                englishTranslateWord.setTranslation(arrWords[i]);
                englishTranslateWord.setEnglishWord(englishWord);
                wordList.add(englishTranslateWord);
            }

            englishWord.setEnglishTranslateWords(wordList);
        }

        englishDictionaryDAO.update(englishWord);
    }

    public void deleteById(Long id) {
        englishDictionaryDAO.deleteByID(id);
    }
}