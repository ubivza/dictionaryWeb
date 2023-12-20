package ru.aleksandr.dictionaryweb.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aleksandr.dictionaryweb.entity.EnglishTranslateWord;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.repository.EngRuRepository;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EnglishDictionaryServiceTests {
    @Mock
    private EngRuRepository engRuRepository;

    @InjectMocks
    private EnglishDictionaryService service;

    @Test
    @DisplayName("Test the word is written to database " +
            "without exceptions list value")
    public void saveStringHappyFlowList() {
        String testWord = "12345 tet, obi, sol";
        EnglishWord englishWord = EnglishWord.builder()
                .id(null)
                .word(12345)
                .build();

        List<EnglishTranslateWord> translates = new ArrayList<>();
        translates.add(EnglishTranslateWord.builder().id(null)
                .englishWord(englishWord).translation("tet")
                .build());
        translates.add(EnglishTranslateWord.builder().id(null)
                .englishWord(englishWord).translation("obi")
                .build());
        translates.add(EnglishTranslateWord.builder().id(null)
                .englishWord(englishWord).translation("sol")
                .build());

        englishWord.setEnglishTranslateWords(translates);
        service.saveString(testWord);

        Mockito.verify(engRuRepository).save(englishWord);
    }

    @Test
    @DisplayName("Test the word is written to database " +
            "without exceptions single value")
    public void saveStringHappyFlowSingleObject() {
        String testWord = "12345 bob";
        EnglishWord englishWord = EnglishWord.builder()
                .id(null)
                .word(12345)
                .build();

        List<EnglishTranslateWord> translates = new ArrayList<>();
        translates.add(EnglishTranslateWord.builder().id(null)
                .englishWord(englishWord).translation("bob")
                .build());

        englishWord.setEnglishTranslateWords(translates);
        service.saveString(testWord);

        Mockito.verify(engRuRepository).save(englishWord);
    }

    @Test
    @DisplayName("Test the word is written to database " +
            "without exceptions no values")
    public void saveStringHappyFlowNoObject() {
        String testWord = "12345 ";
        EnglishWord englishWord = EnglishWord.builder()
                .id(null)
                .word(12345)
                .englishTranslateWords(null)
                .build();

        service.saveString(testWord);

        Mockito.verify(engRuRepository).save(englishWord);
    }
}
