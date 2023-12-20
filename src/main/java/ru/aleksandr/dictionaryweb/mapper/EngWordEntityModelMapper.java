package ru.aleksandr.dictionaryweb.mapper;

import org.mapstruct.Mapper;
import ru.aleksandr.dictionaryweb.entity.EnglishTranslateWord;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.model.EngWordModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class EngWordEntityModelMapper {

    public EnglishWord engWordModelToEnglishWord(EngWordModel model) {
        EnglishWord englishWord = EnglishWord.builder()
                        .word(model.getWord()).build();

        englishWord.setWord(model.getWord());
        if (!model.getTranslations().isBlank() || !(model.getTranslations() == null)) {
            String[] arr = model.getTranslations().split(", ");
            List<EnglishTranslateWord> list = new ArrayList<>();
            for (String translate : arr) {
                list.add(EnglishTranslateWord.builder().translation(translate)
                        .englishWord(englishWord).build());
            }
            englishWord.setEnglishTranslateWords(list);
        }
        englishWord.setEnglishTranslateWords(null);
        return englishWord;
    }

    public EngWordModel englishWordToEngWordModel(EnglishWord entity) {
        EngWordModel engWordModel = EngWordModel.builder()
                .word(entity.getWord()).build();

        if (entity.getEnglishTranslateWords() != null) {
            StringBuilder sb = new StringBuilder();
            for (EnglishTranslateWord englishTranslateWord : entity.getEnglishTranslateWords()) {
                sb.append(", ");
                sb.append(englishTranslateWord.getTranslation());
            }
            sb.replace(0, 2, "");
        } else {
            engWordModel.setTranslations("перевода пока нет");
        }
        return engWordModel;
    }
}