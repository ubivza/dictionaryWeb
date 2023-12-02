package ru.aleksandr.dictionaryweb.dao;



import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.aleksandr.dictionaryweb.entity.EnglishWord;
import ru.aleksandr.dictionaryweb.repository.EngRuRepository;

import java.util.List;

@Component
@Slf4j
public class EnglishDictionaryDAO implements EngRuRepository {

    private final SessionFactory sessionFactory;

    public EnglishDictionaryDAO(SessionFactory sessionFactory) { //почему нет такого бина?
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<EnglishWord> getAll() {
        Session session = sessionFactory.openSession();
        try {
            List<EnglishWord> englishWordList = session.createQuery("from EnglishWord").getResultList();
            return englishWordList;
        } catch (Exception e) {
            log.warn(e + " catched in getAll");
            session.close();
        }
        //подумать чем заменить налл, может кастомный класс ошибку отнаследованную от энтити класса инглишВорд и в нее
        //писать ошибку??
        return null;
    }

    @Override
    @Transactional
    public EnglishWord getByKey(String s) {
        Session session = sessionFactory.openSession();

        try {
            //как тут получить именно по полю(столбцу) word, а не по айди?
            EnglishWord englishWord = session.get(EnglishWord.class, s);
            return englishWord;
        } catch (Exception e) {
            log.warn(e + " catched while getting by key " + s);
            session.close();
            return null;
        }
    }

    @Override
    @Transactional
    public boolean save(EnglishWord ew) {
        Session session = sessionFactory.openSession();

        try {
            /*
            for (EnglishTranslateWord et : ew.getEnglishTranslateWords()) {
                session.persist(et);
                Можно ли не делать этого тут, потому что у меня каскейд тайп в энтити стоит олл?
            }*/
            session.persist(ew);
            return true;
        } catch (Exception e ) {
            log.warn(e + " catched while trying to save entity " + ew);
            session.close();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean update(EnglishWord ew) {
        Session session = sessionFactory.openSession();

        try {
            EnglishWord englishWord = session.get(EnglishWord.class, ew.getId());
            englishWord.setWord(ew.getWord());
            englishWord.setEnglishTranslateWords(ew.getEnglishTranslateWords());

            return true;
        } catch (Exception e) {
            log.warn(e + " catched while trying to update entity " + ew);
            session.close();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteByKey(String s) {
        return false;
    }
}
