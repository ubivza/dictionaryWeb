package ru.aleksandr.dictionaryweb.dao;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
    public boolean save(EnglishWord ew) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        System.out.println("before persist");
        entityManager.persist(ew);
        entityManager.getTransaction().commit();
        System.out.println("after persist");
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
    public boolean deleteByKey(String key) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("delete from EnglishWord where word=:key");
        query.setParameter("key", key);
        return true;
    }

    @Override
    @Transactional
    public EnglishWord getByKey(String key) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("from EnglishWord where word=:key");
        query.setParameter("key", key);

        return (EnglishWord) query.getSingleResult();
    }

    public EnglishWord getByValue(String value) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("from EnglishWord e join e.englishTranslateWords t where t.translation=:translateWord");
        query.setParameter("translateWord", value);

        return (EnglishWord) query.getSingleResult();
    }
}
