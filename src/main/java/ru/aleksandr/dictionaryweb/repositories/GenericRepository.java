package ru.aleksandr.dictionaryweb.repositories;

import java.util.List;

public interface GenericRepository<T, KEY> {
    List<T> getAll();
    T getByKey(KEY key);
    boolean save(T t);
    boolean update(T t);
    boolean deleteByKey(KEY key);
}
