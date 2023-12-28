package ru.aleksandr.dictionaryweb.service;

import org.springframework.stereotype.Component;
import ru.aleksandr.dictionaryweb.db.dao.SpanishDictionaryDAO;
import ru.aleksandr.dictionaryweb.model.SpainWordModel;

import java.util.List;


@Component
public class SpanishDictionaryService {

    private final SpanishDictionaryDAO spanishDictionaryDAO;

    public SpanishDictionaryService(SpanishDictionaryDAO spanishDictionaryDAO) {
        this.spanishDictionaryDAO = spanishDictionaryDAO;
    }

    public SpainWordModel showByKey(String key) {
        return spanishDictionaryDAO.showByKey(key);
    }

    public void save(SpainWordModel model) {
        spanishDictionaryDAO.save(model);
    }

    public void deleteById(Long id) {
        spanishDictionaryDAO.deleteById(id);
    }

    public void updateById(Long id, SpainWordModel word) {
        spanishDictionaryDAO.updateById(id, word);
    }

    public List<SpainWordModel> showAll() {
        return spanishDictionaryDAO.showAll();
    }

    public List<SpainWordModel> showByValue(String value) {
        return spanishDictionaryDAO.showByValue(value);
    }

    public void deleteByKey(String key) {
        spanishDictionaryDAO.deleteByKey(key);
    }
}
