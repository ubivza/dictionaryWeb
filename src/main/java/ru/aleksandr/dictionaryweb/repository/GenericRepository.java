package ru.aleksandr.dictionaryweb.repository;


import java.util.List;

public interface GenericRepository<T, KEY> {
    List<T> getAll();
    T getByKey(KEY key);
    void save(T t);
    void update(T t);
    void deleteByKey(KEY key);
     List<T> getByValue(String value);
    T getById(Long id);
    void deleteById(Long id);
}
