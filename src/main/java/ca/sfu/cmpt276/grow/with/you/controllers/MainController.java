package ca.sfu.cmpt276.grow.with.you.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ca.sfu.cmpt276.grow.with.you.models.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/main")
    public String showMain(HttpSession session) {
        User user = (User) session.getAttribute("session_user");

        if (user == null) {
            return "forward:main.html";
        }

        return "redirect:dashboard";
    }
}
