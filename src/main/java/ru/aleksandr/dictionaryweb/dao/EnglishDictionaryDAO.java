package ru.aleksandr.dictionaryweb.dao;



import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.mapper.EngWordEntityModelMapper;
import ru.aleksandr.dictionaryweb.model.EngWordModel;
import ru.aleksandr.dictionaryweb.repository.EngRuRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EnglishDictionaryDAO implements EngRuRepository {

    private final EntityManager entityManager;
    private final EngWordEntityModelMapper engWordEntityModelMapper;

    public EnglishDictionaryDAO(EntityManager entityManager, EngWordEntityModelMapper engWordEntityModelMapper) {
        this.entityManager = entityManager;
        this.engWordEntityModelMapper = engWordEntityModelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EngWordModel> getAll() {
        List<EnglishWord> result = entityManager.createQuery("from EnglishWord", EnglishWord.class)
                .getResultList();
        return result.stream().map(engWordEntityModelMapper::englishWordToEngWordModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(EngWordModel ewm) {
        EnglishWord englishWord = engWordEntityModelMapper
                .engWordModelToEnglishWord(ewm);
        entityManager.persist(englishWord);
    }

    @Override
    @Transactional
    public void update(Long id, EngWordModel ewm) {
        EnglishWord englishWord = engWordEntityModelMapper
                .engWordModelToEnglishWord(ewm);

        EnglishWord wordToUpdate = getById(id);
        wordToUpdate.setWord(englishWord.getWord());
        wordToUpdate.setEnglishTranslateWords(
                englishWord.getEnglishTranslateWords());
    }

    @Override
    @Transactional
    public void deleteByKey(String key) {

        Query query = entityManager.createQuery("delete from EnglishWord where word=:key");
        query.setParameter("key", key);
        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public EngWordModel getByKey(String key) {

        Query query = entityManager.createQuery("from EnglishWord where word=:key");
        query.setParameter("key", key);
        EnglishWord englishWord = (EnglishWord) query.getSingleResult();
        return engWordEntityModelMapper.englishWordToEngWordModel(englishWord);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EngWordModel> getByValue(String value) {

        Query query = entityManager.createQuery(
                "from EnglishWord e join e.englishTranslateWords t where t.translation=:translateWord");
        query.setParameter("translateWord", value);
        List<EnglishWord> list = query.getResultList();
        return list.stream().map(engWordEntityModelMapper::englishWordToEngWordModel)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        Query query = entityManager.createQuery("delete from EnglishWord where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private EnglishWord getById(Long id) {
        return entityManager.find(EnglishWord.class, id);
    }
}
