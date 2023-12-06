package ru.aleksandr.dictionaryweb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.aleksandr.dictionaryweb.entity.SpanishWord;

@Repository
public interface SpanishRuRepository extends CrudRepository<SpanishWord, String> {
    
}
