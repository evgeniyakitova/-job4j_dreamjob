package ru.job4j.dreamjob.store;

import java.util.Collection;

public interface Store<T> {
    Collection<T> findAll();
    void add(T entity);
    T findById(int id);
    void update(T entity);
}
