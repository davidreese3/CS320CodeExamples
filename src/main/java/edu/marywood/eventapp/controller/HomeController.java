package edu.marywood.eventapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        // Lecture Notes: add later...not right away
        model.addAttribute("message","Hello from the backend");
        return "home";
    }
}
