package com.example.demo;

import com.example.demo.User;
import com.example.demo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/growerdashboard")
    public String growerDashboard(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "grower_dashboard";
    }
     @GetMapping("/admindashboard")
    public String getAdminDashboard(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);// will have to use get all users 
        return "admin_dashboard";
    }
    @GetMapping("/sponsordashboard")
    public String sponsorDashboard(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "sponsor_dashboard";
    }

    @GetMapping("/profile")
    public String userProfile(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "profile";
    }

    // Additional mappings for registration and other pages
}
