package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.models.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest req, HttpServletResponse res, HttpSession session, Model model) {
        User user = (User) session.getAttribute("session_user");

        if (user == null) {
            return "redirect:main.html";
        }

        if (user.getIsAdmin()) {
            List<User> userList = userRepo.findByIsAdmin(false);
            model.addAttribute("userList", userList);
            System.out.println(userList);

            return "protected/admin/dashboard";
        }

        return "protected/user/dashboard";
    }
}
