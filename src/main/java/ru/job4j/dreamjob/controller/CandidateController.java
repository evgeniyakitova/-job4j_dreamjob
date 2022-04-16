package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;
import ru.job4j.dreamjob.store.Store;

@Controller
public class CandidateController {
    private final Store<Candidate> store = CandidateStore.instOf();

    @GetMapping("/candidates")
    public String can(Model model) {
        model.addAttribute("candidates", store.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate() {
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate can) {
        store.add(can);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{canId}")
    public String formUpdateCandidate(Model model, @PathVariable("canId") int id) {
        model.addAttribute("candidate", store.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate can) {
        store.update(can);
        return "redirect:/candidates";
    }
}
