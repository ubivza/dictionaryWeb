package ru.aleksandr.dictionaryweb.dao;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.repository.EngRuRepository;

import java.util.List;

@Component
@Slf4j
public class EnglishDictionaryDAO implements EngRuRepository {

    private final EntityManagerFactory entityManagerFactory;

    public EnglishDictionaryDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    @Transactional
    public List<EnglishWord> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<EnglishWord> englishWordList = entityManager.createQuery("from EnglishWord", EnglishWord.class)
                    .getResultList();
        return englishWordList;
    }

    @Override
    @Transactional
    public EnglishWord getByKey(String s) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("from EnglishWord where word=:wordToFind");
        query.setParameter("wordToFind", s);

        EnglishWord englishWord = (EnglishWord) query.getSingleResult();
        return englishWord;
    }

    @Override
    @Transactional
    public boolean save(EnglishWord ew) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.persist(ew);
        return true;
    }

    @Override
    @Transactional
    public boolean update(EnglishWord ew) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EnglishWord englishWord = entityManager.find(EnglishWord.class, ew.getId());
        englishWord.setWord(ew.getWord());

        englishWord.setEnglishTranslateWords(ew.getEnglishTranslateWords());
        return true;
    }

    @Override
    @Transactional
    public boolean deleteByKey(String s) {
        return false;
    }
}
