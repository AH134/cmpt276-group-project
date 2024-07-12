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
import ca.sfu.cmpt276.utils.enums.UserError;
import ca.sfu.cmpt276.utils.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("username", "");
        model.addAttribute("email", "");
        model.addAttribute("p1", "");
        model.addAttribute("p2", "");
        return "/register.html";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam Map<String, String> newUser, HttpServletResponse res,
            HttpServletRequest req, Model model) {
        try {
            String newName = newUser.get("username");
            String newPwd = newUser.get("password1");
            String newEmail = newUser.get("email");
            String newRole = newUser.get("roleRadio");

            User userByName = userService.getUserByUsername(newName);
            User userByEmail = userService.getUserByEmail(newEmail);
            UserError nameError = UserError.USERNAME_TAKEN;
            UserError emailError = UserError.EMAIL_TAKEN;
            if(userByName != null && userByEmail != null){
                model.addAttribute("error1", nameError.getMessage());
                model.addAttribute("error2", emailError.getMessage());
            }
            else if(userByName != null){
                model.addAttribute("error1", nameError.getMessage());
                model.addAttribute("email", newEmail);
            }
            else if(userByEmail != null) {
                model.addAttribute("error2", emailError.getMessage());
                model.addAttribute("username", newName);
            }
            if(userByName != null || userByEmail != null){
                model.addAttribute("p1", newPwd);
                model.addAttribute("p2", newUser.get("password2"));
                res.setStatus(nameError.getStatusCode());
                return "register";
            }

            User user;
            if (newRole.equalsIgnoreCase(UserRole.GROWER.toString())) {
                user = new Grower(newName, newPwd, newEmail, 1000, 0, 0, 0);
            } else {
                user = new Sponsor(newName, newPwd, newEmail, 1000, 0, 0);
            }
            userService.createUser(user);

            res.setStatus(201);
            req.getSession().setAttribute("session_user", userService.getUserById(user.getUserId()));

            return "redirect:/dashboard";
        } catch (Exception e) {
            res.setStatus(400);
            return "register";
        }
    }
}