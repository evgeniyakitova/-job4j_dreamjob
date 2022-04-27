package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

class CandidateDBStoreTest {
    private static final BasicDataSource POOL = new Main().loadPool();
    private static final CandidateDBStore STORE = new CandidateDBStore(POOL);
    private Candidate candidate;

    @BeforeEach
    public void initCandidate() {
        candidate = new Candidate();
        candidate.setName("name");
        candidate.setDescription("desc");
        candidate.setPhoto(new byte[] {1, 2, 3});
    }

    @Test
    public void whenCreateCandidate() {
        STORE.add(candidate);
        Candidate candidateInDb = STORE.findById(candidate.getId());
        assertEquals(candidateInDb.getName(), candidate.getName());
        assertNotNull(candidate.getCreated());
        assertThat(candidate.getId(), not(0));
    }

    @Test
    public void whenUpdateCandidate() {
        STORE.add(candidate);
        candidate.setName("new_name");
        STORE.update(candidate);
        assertEquals(STORE.findById(candidate.getId()).getName(), "new_name");
    }

    @Test
    public void whenFindById() {
        STORE.add(candidate);
        Candidate candidateInDb = STORE.findById(candidate.getId());
        assertAll(
                () -> assertEquals(candidateInDb.getName(), "name"),
                () -> assertEquals(candidateInDb.getDescription(), "desc"),
                () -> assertArrayEquals(candidateInDb.getPhoto(), new byte[] {1, 2, 3})
        );
    }

    @Test
    public void whenNotFindByIdThenGetNull() {
        assertNull(STORE.findById(-1));
    }

    @Test
    public void whenFindAll() {
        assertEquals(STORE.findAll().size(), 0);
        STORE.add(candidate);
        STORE.add(candidate);
        assertEquals(STORE.findAll().size(), 2);
    }

    @AfterEach
    public void cleanTable() throws SQLException {
        try (Connection cn = POOL.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from candidate")) {
            ps.execute();
        }
    }

    @AfterAll
    public static void closePool() throws SQLException {
        POOL.close();
    }
}