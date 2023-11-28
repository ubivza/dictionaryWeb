package ru.aleksandr.dictionaryweb.dao;

import org.springframework.stereotype.Component;
import ru.aleksandr.dictionaryweb.entities.SpanishWord;
import ru.aleksandr.dictionaryweb.repositories.SpanishRuRepository;

import java.util.List;

@Component
public class SpanishDictionaryDAO {
    private final SpanishRuRepository spanishRuRepository;

    public SpanishDictionaryDAO(SpanishRuRepository spanishRuRepository) {
        this.spanishRuRepository = spanishRuRepository;
    }

    public List<SpanishWord> find() {
        return spanishRuRepository.findAll();
    }
}
