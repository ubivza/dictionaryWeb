package ru.aleksandr.dictionaryweb.dao;



import jakarta.persistence.EntityManager;
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
    public void save(EnglishWord ew) {

        entityManager.persist(ew);
    }

    @Override
    @Transactional
    public void update(EnglishWord ew) {
        entityManager.merge(ew);
    }

    @Override
    @Transactional
    public void deleteByKey(String key) {

        Query query = entityManager.createQuery("delete from EnglishWord where word=:key");
        query.setParameter("key", key);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public EnglishWord getByKey(String key) {

        Query query = entityManager.createQuery("from EnglishWord where word=:key");
        query.setParameter("key", key);

        return (EnglishWord) query.getSingleResult();
    }

    @Transactional
    @Override
    public List<EnglishWord> getByValue(String value) {

        Query query = entityManager.createQuery("from EnglishWord e join e.englishTranslateWords t where t.translation=:translateWord");
        query.setParameter("translateWord", value);

        return query.getResultList();
    }

    @Transactional
    @Override
    public EnglishWord getById(Long id) {
        return entityManager.find(EnglishWord.class, id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        Query query = entityManager.createQuery("delete from EnglishWord where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
