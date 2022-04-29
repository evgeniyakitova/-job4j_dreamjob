package ru.job4j.dreamjob.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDBStore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

@Service
public class CandidateService {
    private final CandidateDBStore store;

    public CandidateService(CandidateDBStore store) {
        this.store = store;
    }

    public Optional<Candidate> add(Candidate candidate) {
        if (candidate.getPhoto().length == 0) {
            try (InputStream inputStream = new ClassPathResource("images/noavatar.jpg").getInputStream()) {
                candidate.setPhoto(inputStream.readAllBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        if (candidate.getPhoto().length == 0) {
            candidate.setPhoto(store.findById(candidate.getId()).getPhoto());
        }
        store.update(candidate);
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }
}
