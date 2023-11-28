package ru.aleksandr.dictionaryweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aleksandr.dictionaryweb.entities.SpanishWord;

@Repository
public interface SpanishRuRepository extends JpaRepository<SpanishWord, String> {
    
}
