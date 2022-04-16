package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore implements Store<Post> {
    private static final PostStore INST = new PostStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(3);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        LocalDateTime dateTime = LocalDateTime.now();
        posts.put(1, new Post(1, "Junior Java Job", "desc1", dateTime));
        posts.put(2, new Post(2, "Middle Java Job", "desc2", dateTime));
        posts.put(3, new Post(3, "Senior Java Job", "desc3", dateTime));
    }

    public static PostStore instOf() {
        return INST;
    }

    public void add(Post post) {
        post.setId(POST_ID.incrementAndGet());
        post.setCreated(LocalDateTime.now());
        posts.put(post.getId(), post);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
