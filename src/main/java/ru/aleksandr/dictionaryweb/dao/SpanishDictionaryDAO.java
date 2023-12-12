package ru.aleksandr.dictionaryweb.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksandr.dictionaryweb.entity.SpanishWord;
import ru.aleksandr.dictionaryweb.repository.SpanishRuRepository;

import java.util.List;

@Component
public class SpanishDictionaryDAO {
    private final SpanishRuRepository spanishRuRepository;

    public SpanishDictionaryDAO(SpanishRuRepository spanishRuRepository) {
        this.spanishRuRepository = spanishRuRepository;
    }

    @Transactional
    public List<SpanishWord> getAll() {
        return (List<SpanishWord>) spanishRuRepository.findAll();
    }

    @Transactional
    public boolean save(SpanishWord sw) {
        spanishRuRepository.save(sw);
        return true;
    }

    @Transactional
    public boolean update(SpanishWord sw) {
        return true;
    }
}
