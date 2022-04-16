package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;
import ru.job4j.dreamjob.store.Store;

import java.util.Collection;

public class PostService {
    private static final PostService INST = new PostService();

    private final Store<Post> store = PostStore.instOf();

    private PostService() {

    }

    public static PostService instOf() {
        return INST;
    }

    public void add(Post post) {
        store.add(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }
}
