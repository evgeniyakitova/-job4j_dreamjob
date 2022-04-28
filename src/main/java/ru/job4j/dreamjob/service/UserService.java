package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserStore;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

    private final UserStore userStore;

    public UserService(UserStore userStore) {
        this.userStore = userStore;
    }

    public Optional<User> add(User user) {
        return Optional.ofNullable(userStore.add(user));
    }

    public User findById(int id) {
        return userStore.findById(id);
    }

    public void update(User user) {
        userStore.update(user);
    }

    public Collection<User> findAll() {
        return userStore.findAll();
    }
}
