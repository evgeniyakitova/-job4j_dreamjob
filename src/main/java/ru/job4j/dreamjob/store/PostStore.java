package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostStore implements Store<Post> {
    private static final PostStore INST = new PostStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(0);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
    }

    public static PostStore instOf() {
        return INST;
    }

    public Optional<Post> add(Post post) {
        post.setId(POST_ID.incrementAndGet());
        post.setCreated(LocalDateTime.now());
        posts.put(post.getId(), post);
        return Optional.of(post);
    }

    @Override
    public Post findById(int id) {
        return posts.get(id);
    }

    @Override
    public void update(Post post) {
        posts.replace(post.getId(), post);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
