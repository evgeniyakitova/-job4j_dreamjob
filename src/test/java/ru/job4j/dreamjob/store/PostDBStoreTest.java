package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class PostDBStoreTest {
    private static final BasicDataSource POOL = new Main().loadPool();
    private static final PostDBStore STORE = new PostDBStore(POOL);
    private Post post;

    @BeforeEach
    public void initPost() {
        post = new Post();
        post.setName("name");
        post.setDescription("desc");
        post.setVisible(true);
        post.setCity(new City(1, "Moscow"));
    }

    @Test
    public void whenCreatePost() {
        STORE.add(post);
        Post postInDb = STORE.findById(post.getId());
        assertEquals(postInDb.getName(), post.getName());
        assertNotNull(post.getCreated());
        assertThat(post.getId(), not(0));
    }

    @Test
    public void whenUpdatePost() {
        STORE.add(post);
        post.setName("new_name");
        STORE.update(post);
        assertEquals(STORE.findById(post.getId()).getName(), "new_name");
    }

    @Test
    public void whenFindById() {
        STORE.add(post);
        Post postInDb = STORE.findById(post.getId());
        assertAll(
                () -> assertEquals(postInDb.getName(), "name"),
                () -> assertEquals(postInDb.getDescription(), "desc"),
                () -> assertTrue(postInDb.isVisible()),
                () -> assertEquals(postInDb.getCity().getId(), 1)
        );
    }

    @Test
    public void whenNotFindByIdThenGetNull() {
        assertNull(STORE.findById(-1));
    }

    @Test
    public void whenFindAll() {
        assertEquals(STORE.findAll().size(), 0);
        STORE.add(post);
        STORE.add(post);
        assertEquals(STORE.findAll().size(), 2);
    }

    @AfterEach
    public void cleanTable() throws SQLException {
        try (Connection cn = POOL.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from post")) {
            ps.execute();
        }
    }

    @AfterAll
    public static void closePool() throws SQLException {
        POOL.close();
    }
}