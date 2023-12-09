package ru.aleksandr.dictionaryweb.dao;



import jakarta.persistence.EntityManager;
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

    private final EntityManager entityManager;

    public EnglishDictionaryDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<EnglishWord> getAll() {

        return entityManager.createQuery("from EnglishWord", EnglishWord.class).getResultList();
    }

    @Override
    @Transactional
    public boolean save(EnglishWord ew) {

        entityManager.persist(ew);
        return true;
    }

    @Override
    @Transactional
    public boolean update(EnglishWord ew) {

        entityManager.merge(ew);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteByKey(String key) {

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("delete from EnglishWord where word=:key");
        query.setParameter("key", key);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public EnglishWord getByKey(String key) {

        Query query = entityManager.createQuery("from EnglishWord where word=:key");
        query.setParameter("key", key);

        return (EnglishWord) query.getSingleResult();
    }

    public List<EnglishWord> getByValue(String value) {

        Query query = entityManager.createQuery("from EnglishWord e join e.englishTranslateWords t where t.translation=:translateWord");
        query.setParameter("translateWord", value);

        //может быть не уникальный результат, обработать ситуацию
        return query.getResultList();
    }
}
