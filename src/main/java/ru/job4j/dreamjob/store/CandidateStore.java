package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore implements Store<Candidate> {
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Anna"));
        candidates.put(2, new Candidate(2, "Dmitriy"));
        candidates.put(3, new Candidate(3, "Pavel"));

    }

    public static CandidateStore instOf() {
        return INST;
    }
    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
