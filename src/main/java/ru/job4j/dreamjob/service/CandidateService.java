package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;
import ru.job4j.dreamjob.store.Store;

import java.util.Collection;

public class CandidateService {
    private static final CandidateService INST = new CandidateService();

    private final Store<Candidate> store = CandidateStore.instOf();

    private CandidateService() {

    }

    public static CandidateService instOf() {
        return INST;
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }
}
