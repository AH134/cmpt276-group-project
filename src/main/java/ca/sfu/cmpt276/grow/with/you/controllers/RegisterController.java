package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.models.UserRepository;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RegisterController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/register")
    public String showRegister(){
        return "registerPage.html";
    }

    @PostMapping("/register/new")
    public String registerUser(@RequestParam Map<String, String> newUser, HttpServletResponse response) {
        try{
            String newName = newUser.get("name");
            String newPwd = newUser.get("password");
            String newEmail = newUser.get("email");
            userRepo.save(new User(newName, newPwd, newEmail, 1000.0, false));
            response.setStatus(201);
            return "dashboard.html";
        }
        catch(Exception e){
            response.setStatus(400);
            return "registerPage.html";
        }
    }
}
