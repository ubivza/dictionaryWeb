package ru.aleksandr.dictionaryweb.mapper;

import org.mapstruct.Mapper;
import ru.aleksandr.dictionaryweb.entity.SpanishWord;
import ru.aleksandr.dictionaryweb.model.SpainWordModel;

@Mapper
public interface SpainWordEntityModelMapper {
    SpanishWord modelToEntity(SpainWordModel spainWordModel);
    SpainWordModel entityToModel(SpanishWord spanishWord);
}
