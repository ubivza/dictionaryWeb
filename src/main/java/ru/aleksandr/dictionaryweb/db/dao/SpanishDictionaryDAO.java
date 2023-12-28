package ru.aleksandr.dictionaryweb.db.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksandr.dictionaryweb.db.entity.SpanishWord;
import ru.aleksandr.dictionaryweb.db.repository.SpanishRuRepository;
import ru.aleksandr.dictionaryweb.mapper.SpainWordEntityModelMapper;
import ru.aleksandr.dictionaryweb.model.SpainWordModel;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SpanishDictionaryDAO {
    private final SpanishRuRepository spanishRuRepository;
    private final SpainWordEntityModelMapper modelMapper;

    public SpanishDictionaryDAO(SpanishRuRepository spanishRuRepository, SpainWordEntityModelMapper modelMapper) {
        this.spanishRuRepository = spanishRuRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public SpainWordModel showByKey(String key) {
        return modelMapper.entityToModel(spanishRuRepository.getByKey(key));
    }

    @Transactional
    public void save(SpainWordModel model) {
        spanishRuRepository.save(modelMapper.modelToEntity(model));
    }

    @Transactional
    public void deleteById(Long id) {
        spanishRuRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Long id, SpainWordModel word) {
        SpanishWord spanishWord = modelMapper.modelToEntity(word);

        SpanishWord wordToUpdate = spanishRuRepository.findById(id)
                .orElse(null);
        wordToUpdate.setWord(spanishWord.getWord());
        wordToUpdate.setSpanishTranslateWords(spanishWord.getSpanishTranslateWords());

        spanishRuRepository.save(wordToUpdate);
    }

    @Transactional(readOnly = true)
    public List<SpainWordModel> showAll() {
        List<SpainWordModel> list = new ArrayList<>();
        spanishRuRepository.findAll().forEach(s -> list.add(modelMapper.entityToModel(s)));
        return list;
    }

    @Transactional(readOnly = true)
    public List<SpainWordModel> showByValue(String value) {
        List<SpainWordModel> list = new ArrayList<>();
        for (SpanishWord sw : spanishRuRepository.findByValue(value)) {
            list.add(modelMapper.entityToModel(sw));
        }
        return list;
    }

    @Transactional
    public void deleteByKey(String key) {
        spanishRuRepository.deleteByWord(key);
    }
}
