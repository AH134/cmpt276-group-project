package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
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

    @Autowired
    private PlantService plantService;

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest req, HttpServletResponse res, HttpSession session, Model model) {
        User user = (User) session.getAttribute("session_user");
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setHeader("Expires", "0");

        if (user == null) {
            return "redirect:main";
        }

        if (user.getRole() == UserRole.ADMIN) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("userList", users);

            return "protected/admin/dashboard";
        }

        if (user.getRole() == UserRole.GROWER) {
            List<Plant> plantsList = plantService.getPlantsByGrower((Grower) user);
            model.addAttribute("activeListings", plantsList.size());
            model.addAttribute("lifetimeListings", ((Grower) user).getPlantsGrown());
            return "protected/user/growerDashboard";
        }

        if (user.getRole() == UserRole.SPONSOR) {
            List<Plant> plantsList = plantService.getPlantsBySponsor((Sponsor) user);
            model.addAttribute("activeSponsors", plantsList.size());
            model.addAttribute("lifetimeSponsors", ((Sponsor) user).getPlantsSponsored());
            return "protected/user/sponsorDashboard";
        }

        return "protected/user/sponsorDashboard";
    }
}
