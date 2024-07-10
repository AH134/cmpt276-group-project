package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegister() {
        return "forward:/register.html";
    }

    @PostMapping("/register/new")
    public String registerUser(@RequestParam Map<String, String> newUser, HttpServletResponse response,
            HttpServletRequest req) {
        try {
            System.out.println(newUser);
            String newName = newUser.get("username");
            String newPwd = newUser.get("password1");
            String newEmail = newUser.get("email");
            String newRole = newUser.get("roleRadio");

            User user;
            if (newRole.equalsIgnoreCase(UserRole.GROWER.toString())) {
                user = new Grower(newName, newPwd, newEmail, 1000, 0, 0, 0);
            } else {
                user = new Sponsor(newName, newPwd, newEmail, 1000, 0, 0);
            }
            userService.createUser(user);

            response.setStatus(201);
            req.getSession().setAttribute("session_user", userService.getUserById(user.getUserId()));

            return "redirect:/dashboard";
        } catch (Exception e) {
            response.setStatus(400);
            return "forward:/register.html";
        }
    }
}