package ca.sfu.cmpt276.grow.with.you.controllers;

import ca.sfu.cmpt276.grow.with.you.models.User;

import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String userProfile(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("session_user");
        model.addAttribute("user", loggedInUser);
        model.addAttribute("profile", loggedInUser.getProfile());
        return "protected/user/profile";
    }
}
