package ca.sfu.cmpt276.grow.with.you.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showMain(HttpSession session) {
        User user = userService.getUserBySession(session);
        if (user == null) {
            return "forward:main.html";
        }

        return "redirect:dashboard";
    }

    @GetMapping("/main")
    public String showMainExplicit(HttpSession session) {
        User user = userService.getUserBySession(session);
        if (user == null) {
            return "forward:main.html";
        }

        return "redirect:dashboard";
    }
}
