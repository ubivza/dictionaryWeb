package ru.aleksandr.dictionaryweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aleksandr.dictionaryweb.entity.SpanishWord;

@Repository
public interface SpanishRuRepository extends JpaRepository<SpanishWord, String> {
    
}
