package ru.aleksandr.dictionaryweb.util;

import org.springframework.stereotype.Component;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;

import java.util.ArrayList;
import java.util.List;

@Component
public class EngRuMessageFormatter {
    public StringBuffer listToStringMessageFormatter(List<EnglishWord> englishWords) {
        StringBuffer sb = new StringBuffer();
        switch (englishWords.size()) {
            case 0:
                sb.append("Значение по заданным параметрам не найдено в англо-русском словаре");
                break;
            case 1:
                EnglishWord englishWord = englishWords.get(0);
                switch (englishWord.getEnglishTranslateWords().size()) {
                    case 0:
                        sb.append(englishWord.getWord() + " - " + " перевода пока нет");
                        break;
                    case 1:
                        sb.append(englishWord.getWord() +
                                " - " + englishWord.getEnglishTranslateWords().get(0).getTranslation());
                        break;
                    default:
                        sb.append(englishWord.getWord() + " - " + englishWord.getEnglishTranslateWords().get(0).getTranslation());
                        for (int i = 1; i < englishWord.getEnglishTranslateWords().size(); i++) {
                            sb.append(", ");
                            sb.append(englishWord.getEnglishTranslateWords().get(i).getTranslation());
                        }
                        break;
                }
                break;
            default:
                for(int i = 0; i < englishWords.size(); i++) {
                    EnglishWord engWord = englishWords.get(i);
                    switch (engWord.getEnglishTranslateWords().size()) {
                        case 0:
                            sb.append(engWord.getWord() + " - " + " перевода пока нет");
                            sb.append("\n");
                            break;
                        case 1:
                            sb.append(engWord.getWord() +
                                    " - " + engWord.getEnglishTranslateWords().get(0).getTranslation());
                            sb.append("\n");
                            break;
                        default:
                            sb.append(engWord.getWord() + " - " + engWord.getEnglishTranslateWords().get(0).getTranslation());
                            for (int j = 1; j < engWord.getEnglishTranslateWords().size(); j++) {
                                sb.append(", ");
                                sb.append(engWord.getEnglishTranslateWords().get(j).getTranslation());
                            }
                            sb.append("\n");
                            break;
                    }
                }
                break;
        }
        return sb;
    }

    public List<String> listToListMessageFormatter(List<EnglishWord> englishWords) {
        List<String> list = new ArrayList<>();
        for (EnglishWord ew : englishWords) {
            StringBuffer translations = new StringBuffer();

            if (ew.getEnglishTranslateWords().isEmpty()) {
                translations.append("перевода пока нет");
            } else {

                translations.append(ew.getEnglishTranslateWords().get(0).getTranslation());
                for (int i = 1; i < ew.getEnglishTranslateWords().size(); i++) {
                    translations.append(", ");
                    translations.append(ew.getEnglishTranslateWords().get(i).getTranslation());
                }
            }
            list.add(ew.getWord() + " - " + translations);
        }
        return list;
    }
}
