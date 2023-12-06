package ru.aleksandr.dictionaryweb.dao;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
    public EnglishWord getByKey(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EnglishWord englishWord = entityManager.find(EnglishWord.class, id);

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

        entityManager.merge(ew);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteByKey(Long id) {
        return false;
    }
}
