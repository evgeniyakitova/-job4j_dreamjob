package ru.job4j.dreamjob.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CandidateService;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class CandidateController {
    private final CandidateService service;

    public CandidateController(CandidateService service) {
        this.service = service;
    }

    @GetMapping("/candidates")
    public String can(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("candidates", service.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate can,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        can.setPhoto(file.getBytes());
        service.add(can);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{canId}")
    public String formUpdateCandidate(Model model, @PathVariable("canId") int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("candidate", service.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate can,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        can.setPhoto(file.getBytes());
        service.update(can);
        return "redirect:/candidates";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) throws IOException {
        Candidate candidate = service.findById(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
