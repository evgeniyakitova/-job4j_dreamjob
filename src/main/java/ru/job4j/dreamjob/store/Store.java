package ru.job4j.dreamjob.store;

import java.util.Collection;

public interface Store<T> {
    Collection<T> findAll();
}
