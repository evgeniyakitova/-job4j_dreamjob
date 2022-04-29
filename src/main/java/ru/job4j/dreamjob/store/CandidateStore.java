package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CandidateStore implements Store<Candidate> {
    private static final CandidateStore INST = new CandidateStore();
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(0);

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Optional<Candidate> add(Candidate candidate) {
        candidate.setId(CANDIDATE_ID.incrementAndGet());
        candidate.setCreated(LocalDateTime.now());
        candidates.put(candidate.getId(), candidate);
        return Optional.of(candidate);
    }

    @Override
    public Candidate findById(int id) {
        return candidates.get(id);
    }

    @Override
    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
