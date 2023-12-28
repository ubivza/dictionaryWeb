package ru.aleksandr.dictionaryweb.mapper;

import org.mapstruct.Mapper;
import ru.aleksandr.dictionaryweb.db.entity.SpanishTranslateWord;
import ru.aleksandr.dictionaryweb.db.entity.SpanishWord;
import ru.aleksandr.dictionaryweb.model.SpainWordModel;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class SpainWordEntityModelMapper {
    public SpanishWord modelToEntity(SpainWordModel model) {
        if (model == null) {
            return null;
        }
        SpanishWord spanishWord = SpanishWord.builder()
                .word(model.getWord()).build();
        if (model.getId() != null) {
            spanishWord.setId(model.getId());
        }

        spanishWord.setWord(model.getWord());
        if (!model.getTranslations().isBlank()) {
            String[] arr = model.getTranslations().split(", ");
            List<SpanishTranslateWord> list = new ArrayList<>();
            for (String translate : arr) {
                list.add(SpanishTranslateWord.builder().translation(translate)
                        .spanishWord(spanishWord).build());
            }
            spanishWord.setSpanishTranslateWords(list);
        } else {
            spanishWord.setSpanishTranslateWords(null);
        }
        return spanishWord;
    }
    public SpainWordModel entityToModel(SpanishWord entity) {
        if (entity == null) {
            return null;
        }
        SpainWordModel spainWordModel = SpainWordModel.builder()
                .id(entity.getId()).word(entity.getWord()).build();

        if (entity.getSpanishTranslateWords() != null
                && !entity.getSpanishTranslateWords().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (SpanishTranslateWord spanishTranslateWord :
                    entity.getSpanishTranslateWords()) {
                sb.append(", ");
                sb.append(spanishTranslateWord.getTranslation());
            }
            sb.replace(0, 2, "");
            spainWordModel.setTranslations(sb.toString());
        } else {
            spainWordModel.setTranslations(null);
        }
        return spainWordModel;
    }
}
