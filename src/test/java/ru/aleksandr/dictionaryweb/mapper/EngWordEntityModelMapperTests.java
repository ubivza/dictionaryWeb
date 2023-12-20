package ru.aleksandr.dictionaryweb.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aleksandr.dictionaryweb.entity.EnglishTranslateWord;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.model.EngWordModel;

import java.util.ArrayList;
import java.util.List;

public class EngWordEntityModelMapperTests {

    @Test
    @DisplayName("Test is mapping right multiple values " +
            "without exceptions")
    public void mapModelToEntityHappy() {
        EngWordEntityModelMapper modelMapper =
                new EngWordEntityModelMapper();
        EngWordModel model = EngWordModel.builder()
                .word(12345).translations("lom, som")
                .build();

        EnglishWord englishWord = EnglishWord.builder()
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
        EngWordEntityModelMapper modelMapper =
                new EngWordEntityModelMapper();
        EngWordModel model = EngWordModel.builder()
                .word(12345).translations("lom")
                .build();

        EnglishWord englishWord = EnglishWord.builder()
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
        EngWordEntityModelMapper modelMapper =
                new EngWordEntityModelMapper();
        EngWordModel model = EngWordModel.builder()
                .word(12345).translations("")
                .build();

        EnglishWord englishWord = EnglishWord.builder()
                .word(12345).build();

        Assertions.assertEquals(englishWord,
                modelMapper.engWordModelToEnglishWord(model));
    }
}
