package ru.aleksandr.dictionaryweb.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;

import java.util.ArrayList;
import java.util.List;

public class EngRuMessageFormatterTests {

    @Test
    @DisplayName("Zero words case")
    public void listMessageFormatterZero() {
        EngRuMessageFormatter messageFormatter = new EngRuMessageFormatter();
        List<EnglishWord> wordList = new ArrayList<>();
        StringBuffer rightValue = new StringBuffer()
                .append("Значение по заданным параметрам не найдено в " +
                        "англо-русском словаре ");
        StringBuffer result = messageFormatter
                .listToStringMessageFormatter(wordList);

        Assertions.assertTrue(rightValue.toString().equals(result.toString()));
    }
}
