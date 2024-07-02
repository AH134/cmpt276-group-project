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
public class LoginController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/login")
    public String getLogin(HttpServletRequest req,
            HttpServletResponse res, HttpSession session, Model model) {
        User user = (User) session.getAttribute("session_user");

        if (user == null) {
            return "login";
        }

        return "redirect:dashboard";
    }

    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> loginData, HttpServletRequest req,
            HttpServletResponse res, Model model) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        List<User> userList = userRepo.findByUsernameAndPassword(username, password);

        if (userList.isEmpty()) {
            UserError error = UserError.USER_NOT_FOUND;
            model.addAttribute("error", error.getMessage());
            res.setStatus(error.getStatusCode());

            return "login";
        }

        User user = userList.getFirst();
        req.getSession().setAttribute("session_user", user);

        return "redirect:dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse res) {
        req.getSession().invalidate();
        return "redirect:main.html";
    }

}
