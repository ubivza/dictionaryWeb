package ru.aleksandr.dictionaryweb.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import ru.aleksandr.dictionaryweb.models.SpanishRuDictionaryWord;
import ru.aleksandr.dictionaryweb.utils.SpanishRuDictValidator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//@Repository
@Slf4j
public class SpanishRuRepositoryImpl implements SpanishRuRepository, Cacheable {
    private final Properties properties;
    private final String FILE_NAME = "src/main/resources/static/dictionary2.properties";
    private SpanishRuDictValidator spanishRuDictValidator;
    private Map<String, String> cacheMap = new HashMap<>();
    private boolean isNotChanged = true;

    @Autowired
    public SpanishRuRepositoryImpl(SpanishRuDictValidator spanishRuDictValidator) {
        this.spanishRuDictValidator = spanishRuDictValidator;
        this.properties = new Properties();

        try(InputStream in = new FileInputStream(FILE_NAME)) {
            properties.load(in);

            cacheMap.putAll((Map) properties);
            log.info("CacheMap of second dict was filled with data from properties file");

            properties.clear();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such properties file while loading");
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while loading properties file");
        }
    }


    public Map<String, String> getAll() {
        return cacheMap;
    }

    public String getByKey(String s) {
        if (!cacheMap.containsKey(s))
            return "Key not found, try again";
        return cacheMap.get(s);
    }

    public boolean save(String s) {
        String[] valueToSave = s.trim().split(" - ", 2);

        SpanishRuDictionaryWord word = new SpanishRuDictionaryWord();
        word.setSpanishWord(valueToSave[0]);
        word.setRuWord(valueToSave[1]);

        DataBinder dataBinder = new DataBinder(word);
        dataBinder.addValidators(spanishRuDictValidator);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            for (ObjectError str : dataBinder.getBindingResult().getAllErrors()) {
                System.out.println(str.getDefaultMessage());
            }
            System.out.println();
            log.warn("{} is not valid data to add,\nValidation message: {}\n",
                    s, dataBinder.getBindingResult().getAllErrors());
            return false;
        }

        cacheMap.put(word.getSpanishWord(), word.getRuWord());
        isNotChanged = false;
        return true;
    }

    public boolean update(String s) {
        log.info("Trying to update {}", s);
        String[] valueToUpdate = s.trim().split(" - ", 2);

        if (cacheMap.containsKey(valueToUpdate[0])) {
            cacheMap.put(valueToUpdate[0], valueToUpdate[1]);
            isNotChanged = false;
            return true;
        }
        return false;
    }

    public boolean deleteByKey(String s) {
        log.info("Trying to delete {}", s);
        if (cacheMap.containsKey(s)) {
            cacheMap.remove(s);
            isNotChanged = false;
            return true;
        }
        return false;
    }

    public void saveCacheToMemory() {
        if (isNotChanged) {
            log.info("There were no changes in file, cacheMap of second dict is not pushing");
            return;
        }

        try(OutputStream out = new FileOutputStream(FILE_NAME)) {
            properties.putAll(cacheMap);
            log.info("CacheMap of second dict is pushing to properties file");
            properties.store(out, null);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such properties file while saving");
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while saving properties file");
        }
    }
}
