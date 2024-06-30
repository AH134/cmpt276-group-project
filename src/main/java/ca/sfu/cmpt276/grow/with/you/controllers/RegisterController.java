package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.models.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RegisterController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/register")
    public String showRegister() {
        return "redirect:register.html";
    }

    @PostMapping("/register/new")
    public String registerUser(@RequestParam Map<String, String> newUser, HttpServletResponse response,
            HttpServletRequest req) {
        try {
            System.out.println(newUser);
            String newName = newUser.get("username");
            String newPwd = newUser.get("password1");
            String newEmail = newUser.get("email");
            userRepo.save(new User(newName, newPwd, newEmail, 1000.0, false));
            response.setStatus(201);

            List<User> user = userRepo.findByUsernameAndPassword(newName, newPwd);
            req.getSession().setAttribute("session_user", user.getFirst());

            return "redirect:/dashboard";
        } catch (Exception e) {
            response.setStatus(400);
            return "redirect:register.html";
        }
    }
}
