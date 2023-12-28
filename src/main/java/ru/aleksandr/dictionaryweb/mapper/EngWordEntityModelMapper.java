package ru.aleksandr.dictionaryweb.mapper;

import org.mapstruct.Mapper;
import ru.aleksandr.dictionaryweb.db.entity.EnglishTranslateWord;
import ru.aleksandr.dictionaryweb.db.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.model.EngWordModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class EngWordEntityModelMapper {

    public EnglishWord engWordModelToEnglishWord(EngWordModel model) {
        if (model == null) {
            return null;
        }
        EnglishWord englishWord = EnglishWord.builder()
                        .word(Integer.valueOf(model.getWord())).build();
        if (model.getId() != null) {
            englishWord.setId(model.getId());
        }
        englishWord.setWord(Integer.valueOf(model.getWord()));
        if (!model.getTranslations().isBlank()) {
            String[] arr = model.getTranslations().split(", ");
            List<EnglishTranslateWord> list = new ArrayList<>();
            for (String translate : arr) {
                list.add(EnglishTranslateWord.builder().translation(translate)
                        .englishWord(englishWord).build());
            }
            englishWord.setEnglishTranslateWords(list);
        } else {
            englishWord.setEnglishTranslateWords(null);
        }
        return englishWord;
    }

    public EngWordModel englishWordToEngWordModel(EnglishWord entity) {
        if (entity == null) {
            return null;
        }
        EngWordModel engWordModel = EngWordModel.builder().id(entity.getId())
                .word(entity.getWord().toString()).build();

        if (entity.getEnglishTranslateWords() != null
                && !entity.getEnglishTranslateWords().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (EnglishTranslateWord englishTranslateWord :
                    entity.getEnglishTranslateWords()) {
                sb.append(", ");
                sb.append(englishTranslateWord.getTranslation());
            }
            sb.replace(0, 2, "");
            engWordModel.setTranslations(sb.toString());
        } else {
            engWordModel.setTranslations(null);
        }
        return engWordModel;
    }
}
