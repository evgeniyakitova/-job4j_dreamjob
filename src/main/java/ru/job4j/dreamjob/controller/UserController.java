package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user, HttpSession session) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "registration";
        }
        session.setAttribute("user", regUser.get());
        return "redirect:/index";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session) {
        Optional<User> userDb = userService.findUserByEmailAndPwd(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
