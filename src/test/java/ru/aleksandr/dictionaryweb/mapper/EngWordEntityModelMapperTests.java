package ru.aleksandr.dictionaryweb.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aleksandr.dictionaryweb.db.entity.EnglishTranslateWord;
import ru.aleksandr.dictionaryweb.db.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.model.EngWordModel;

import java.util.ArrayList;
import java.util.List;

public class EngWordEntityModelMapperTests {
    private EngWordEntityModelMapper modelMapper;
    @BeforeEach
    public void createModelMapper() {
        modelMapper = new EngWordEntityModelMapper();
    }


    @Test
    @DisplayName("Test is mapping right multiple values " +
            "without exceptions")
    public void mapModelToEntityHappyMultiple() {
        EngWordModel model = EngWordModel.builder().id(1L)
                .word("12345").translations("lom, som")
                .build();

        EnglishWord englishWord = EnglishWord.builder()
                .id(1L)
                .word(12345).build();

        List<EnglishTranslateWord> wordList = new ArrayList<>();
        wordList.add(EnglishTranslateWord.builder().translation("lom")
                .englishWord(englishWord).build());
        wordList.add(EnglishTranslateWord.builder().translation("som")
                .englishWord(englishWord).build());

        englishWord.setEnglishTranslateWords(wordList);
        Assertions.assertEquals(englishWord,
                modelMapper.engWordModelToEnglishWord(model));
    }

    @Test
    @DisplayName("Test is mapping right one value " +
            "without exceptions")
    public void mapModelToEntityHappyOne() {
        EngWordModel model = EngWordModel.builder().id(1L)
                .word("12345").translations("lom")
                .build();

        EnglishWord englishWord = EnglishWord.builder().id(1L)
                .word(12345).build();

        List<EnglishTranslateWord> wordList = new ArrayList<>();
        wordList.add(EnglishTranslateWord.builder().translation("lom")
                .englishWord(englishWord).build());

        englishWord.setEnglishTranslateWords(wordList);
        Assertions.assertEquals(englishWord,
                modelMapper.engWordModelToEnglishWord(model));
    }

    @Test
    @DisplayName("Test is mapping right zero value " +
            "without exceptions")
    public void mapModelToEntityHappyZero() {
        EngWordModel model = EngWordModel.builder()
                .word("12345").translations("")
                .build();

        EnglishWord englishWord = EnglishWord.builder()
                .word(12345).englishTranslateWords(null)
                .build();

        Assertions.assertEquals(englishWord,
                modelMapper.engWordModelToEnglishWord(model));
    }

    @Test
    @DisplayName("Test is mapping right multiple value " +
            "without exceptions")
    public void mapEntityToModelHappyMultiple() {
        EnglishWord ew = EnglishWord.builder()
                .id(1L).word(12345).build();

        List<EnglishTranslateWord> wordList = new ArrayList<>();
        wordList.add(EnglishTranslateWord.builder().id(1L).translation("lom")
                .englishWord(ew).build());
        wordList.add(EnglishTranslateWord.builder().id(2L).translation("som")
                .englishWord(ew).build());

        ew.setEnglishTranslateWords(wordList);

        EngWordModel ewm = EngWordModel.builder().id(1L)
                        .word("12345").translations("lom, som")
                        .build();

        Assertions.assertEquals(ewm, modelMapper.englishWordToEngWordModel(ew));
    }

    @Test
    @DisplayName("Test is mapping right one value " +
            "without exceptions")
    public void mapEntityToModelHappyOne() {
        EnglishWord ew = EnglishWord.builder()
                .id(1L).word(12345).build();

        List<EnglishTranslateWord> wordList = new ArrayList<>();
        wordList.add(EnglishTranslateWord.builder().id(1L).translation("lom")
                .englishWord(ew).build());

        ew.setEnglishTranslateWords(wordList);

        EngWordModel ewm = EngWordModel.builder().id(1L)
                .word("12345").translations("lom")
                .build();

        Assertions.assertEquals(ewm, modelMapper.englishWordToEngWordModel(ew));
    }

    @Test
    @DisplayName("Test is mapping right zero value " +
            "without exceptions")
    public void mapEntityToModelHappyZero() {
        EnglishWord ew = EnglishWord.builder()
                .word(12345).englishTranslateWords(null).build();

        EngWordModel ewm = EngWordModel.builder()
                .word("12345").translations(null).build();
        Assertions.assertEquals(ewm, modelMapper.englishWordToEngWordModel(ew));
    }
}
