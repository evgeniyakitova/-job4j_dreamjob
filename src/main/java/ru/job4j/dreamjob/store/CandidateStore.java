package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore implements Store<Candidate> {
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        LocalDateTime dateTime = LocalDateTime.now();
        candidates.put(1, new Candidate(1, "Anna", "desc1", dateTime));
        candidates.put(2, new Candidate(2, "Dmitriy", "desc2", dateTime));
        candidates.put(3, new Candidate(3, "Pavel", "desc3", dateTime));

    }

    public static CandidateStore instOf() {
        return INST;
    }

    public void add(Candidate candidate) {

    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
