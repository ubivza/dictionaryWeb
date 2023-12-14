package ru.aleksandr.dictionaryweb.util;

import org.springframework.stereotype.Component;
import ru.aleksandr.dictionaryweb.entity.SpanishWord;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpanishRuMessageFormatter {
    public List<String> listToListMessageFormatter(List<SpanishWord> wordList) {
        List<String> list = new ArrayList<>();
        for (SpanishWord ew : wordList) {
            StringBuffer translations = new StringBuffer();

            if (ew.getSpanishTranslateWords().isEmpty()) {
                translations.append("перевода пока нет");
            } else {

                translations.append(ew.getSpanishTranslateWords().get(0).getTranslation());
                for (int i = 1; i < ew.getSpanishTranslateWords().size(); i++) {
                    translations.append(", ");
                    translations.append(ew.getSpanishTranslateWords().get(i).getTranslation());
                }
            }
            list.add(ew.getWord() + " - " + translations);
        }
        return list;
    }

    public StringBuffer listToStringMessageFormatter(List<SpanishWord> wordList) {
        StringBuffer sb = new StringBuffer();
        switch (wordList.size()) {
            case 0:
                sb.append("Значение по заданным параметрам не найдено в испано-русском словаре ");
                break;
            case 1:
                SpanishWord spanishWord = wordList.get(0);
                switch (spanishWord.getSpanishTranslateWords().size()) {
                    case 0:
                        sb.append(spanishWord.getWord() + " - " + " перевода пока нет");
                        break;
                    case 1:
                        sb.append(spanishWord.getWord() +
                                " - " + spanishWord.getSpanishTranslateWords().get(0).getTranslation());
                        break;
                    default:
                        sb.append(spanishWord.getWord() + " - " + spanishWord.getSpanishTranslateWords().get(0).getTranslation());
                        for (int i = 1; i < spanishWord.getSpanishTranslateWords().size(); i++) {
                            sb.append(", ");
                            sb.append(spanishWord.getSpanishTranslateWords().get(i).getTranslation());
                        }
                        break;
                }
                break;
            default:
                for(int i = 0; i < wordList.size(); i++) {
                    SpanishWord spanWord = wordList.get(i);
                    switch (spanWord.getSpanishTranslateWords().size()) {
                        case 0:
                            sb.append(spanWord.getWord() + " - " + " перевода пока нет");
                            sb.append("\n");
                            break;
                        case 1:
                            sb.append(spanWord.getWord() +
                                    " - " + spanWord.getSpanishTranslateWords().get(0).getTranslation());
                            sb.append("\n");
                            break;
                        default:
                            sb.append(spanWord.getWord() + " - " + spanWord.getSpanishTranslateWords().get(0).getTranslation());
                            for (int j = 1; j < spanWord.getSpanishTranslateWords().size(); j++) {
                                sb.append(", ");
                                sb.append(spanWord.getSpanishTranslateWords().get(j).getTranslation());
                            }
                            sb.append("\n");
                            break;
                    }
                }
                break;
        }
        return sb;
    }
}
