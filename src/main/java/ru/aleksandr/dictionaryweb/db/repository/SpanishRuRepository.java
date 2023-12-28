package ru.aleksandr.dictionaryweb.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksandr.dictionaryweb.db.entity.SpanishWord;

import java.util.List;

@Repository
public interface SpanishRuRepository extends JpaRepository<SpanishWord, Long> {


    @Query("select s from SpanishWord s where s.word=?1")
    SpanishWord getByKey(String key);

    @Query("from SpanishWord s join s.spanishTranslateWords e where e.translation=?1")
    List<SpanishWord> findByValue(String value);

    void deleteByWord(String word);
}
