package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sfu.cmpt276.grow.with.you.models.Profile;
import ca.sfu.cmpt276.grow.with.you.models.ProfileRepository;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.models.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RegisterController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProfileRepository profileRepo;

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
            int newRole = Integer.parseInt(newUser.get("roleRadio"));

            User user = new User(newName, newPwd, newRole, newEmail, 1000.0, false, null);
            userRepo.save(user);

            Profile userProfile = new Profile(user, 0, 0);
            profileRepo.save(userProfile);

            user.setProfile(userProfile);
            userRepo.save(user);

            response.setStatus(201);

            List<User> userList = userRepo.findByUsernameAndPassword(newName, newPwd);
            req.getSession().setAttribute("session_user", userList.get(0));

            return "redirect:/dashboard";
        } catch (Exception e) {
            response.setStatus(400);
            return "forward:/register.html";
        }
    }
}