package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest req, HttpServletResponse res, HttpSession session, Model model) {
        User user = (User) session.getAttribute("session_user");

        if (user == null) {
            return "redirect:main";
        }

        if (user.getRole() == UserRole.ADMIN) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("userList", users);

            return "protected/admin/dashboard";
        }

        if (user.getRole() == UserRole.GROWER) {
            return "protected/user/growerDashboard";
        }

        return "protected/user/sponsorDashboard";
    }
}
