package ru.aleksandr.dictionaryweb.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksandr.dictionaryweb.db.entity.SpanishWord;
import ru.aleksandr.dictionaryweb.mapper.SpainWordEntityModelMapper;
import ru.aleksandr.dictionaryweb.model.SpainWordModel;
import ru.aleksandr.dictionaryweb.db.repository.SpanishRuRepository;

import java.util.ArrayList;
import java.util.List;


@Component
public class SpanishDictionaryService {

    private final SpanishRuRepository spanishRuRepository;
    private final SpainWordEntityModelMapper modelMapper;

    public SpanishDictionaryService(SpanishRuRepository spanishRuRepository, SpainWordEntityModelMapper modelMapper) {
        this.spanishRuRepository = spanishRuRepository;
        this.modelMapper = modelMapper;
    }

    public SpainWordModel showByKey(String key) {
        return modelMapper.entityToModel(spanishRuRepository.getByKey(key));
    }

    public void saveString(SpainWordModel model) {
        spanishRuRepository.save(modelMapper.modelToEntity(model));
    }

    public void deleteById(Long id) {
        spanishRuRepository.deleteById(id);
    }

    //как перенести в слой репозитория спринг даты?
    @Transactional
    public void updateById(Long id, SpainWordModel word) {
        SpanishWord spanishWord = modelMapper.modelToEntity(word);

        SpanishWord wordToUpdate = spanishRuRepository.findById(id)
                .orElse(null);
        wordToUpdate.setWord(spanishWord.getWord());
        wordToUpdate.setSpanishTranslateWords(spanishWord.getSpanishTranslateWords());

        spanishRuRepository.save(wordToUpdate);
    }

    public List<SpainWordModel> showAll() {
        List<SpainWordModel> list = new ArrayList<>();
        spanishRuRepository.findAll().forEach(s -> list.add(modelMapper.entityToModel(s)));
        return list;
    }

    public List<SpainWordModel> showByValue(String value) {
        List<SpainWordModel> list = new ArrayList<>();
        for (SpanishWord sw : spanishRuRepository.findByValue(value)) {
            list.add(modelMapper.entityToModel(sw));
        }
        return list;
    }

    public void deleteByKey(String key) {
        spanishRuRepository.deleteByWord(key);
    }
}
