package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.models.UserRepository;
import ca.sfu.cmpt276.utils.enums.UserError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/login")
    public String getLogin(@RequestParam Map<String, String> loginData, Model model, HttpServletRequest req,
            HttpServletResponse res, HttpSession session) {
        User user = (User) session.getAttribute("session_user");

        if (user == null) {
            return "users/login";
        }

        model.addAttribute("user", user);

        return "protected/home";
    }

    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> loginData, Model model, HttpServletRequest req,
            HttpServletResponse res) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        List<User> userList = userRepo.findByUsernameAndPassword(username, password);

        if (userList.isEmpty()) {
            UserError error = UserError.USER_NOT_FOUND;
            model.addAttribute("error", error);
            res.setStatus(error.getStatusCode());

            return "users/login";
        }

        User user = userList.get(0);
        req.getSession().setAttribute("session_user", user);
        model.addAttribute("user", user);

        return "protected/home";
    }

}
