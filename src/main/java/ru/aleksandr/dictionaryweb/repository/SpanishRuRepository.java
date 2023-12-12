package ru.aleksandr.dictionaryweb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksandr.dictionaryweb.entity.SpanishWord;

@Repository
@Transactional
public interface SpanishRuRepository extends CrudRepository<SpanishWord, String> {
    
}
