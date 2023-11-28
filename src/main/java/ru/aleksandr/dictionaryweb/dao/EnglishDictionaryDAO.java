package ru.aleksandr.dictionaryweb.dao;


import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.aleksandr.dictionaryweb.entities.EnglishWord;
import ru.aleksandr.dictionaryweb.repositories.EngRuRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EnglishDictionaryDAO implements EngRuRepository {

    private final SessionFactory sessionFactory;

    public EnglishDictionaryDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<EnglishWord> getAll() {
        Session session = sessionFactory.openSession();

        List<EnglishWord> englishWordList = session.createQuery("from EnglishWord").getResultList();
        Map<String, String> resultMap = new HashMap<>();

        /* Перенести в сервис слой
        for (EnglishWord ew : englishWordList) {
            StringBuilder stringBuilder = new StringBuilder((CharSequence) ew.getEnglishTranslateWords().get(0).getTranslation());
            if (ew.getEnglishTranslateWords().size() > 1) {
                for (EnglishTranslateWord et : ew.getEnglishTranslateWords()) {
                    stringBuilder.append(", ");
                    stringBuilder.append(et);
                }
            }
            resultMap.put(String.valueOf(ew.getWord()), stringBuilder.toString());
        }*/
        return englishWordList;
    }

    @Override
    public EnglishWord getByKey(String s) {
        return null;
    }

    @Override
    public boolean save(EnglishWord ew) {
        return false;
    }

    @Override
    public boolean update(EnglishWord ew) {
        return false;
    }

    @Override
    public boolean deleteByKey(String s) {
        return false;
    }
}
