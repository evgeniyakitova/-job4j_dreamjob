package ru.job4j.dreamjob.store;

import java.util.Collection;
import java.util.Optional;

public interface Store<T> {
    Collection<T> findAll();
    Optional<T> add(T entity);
    T findById(int id);
    void update(T entity);
}
