package ca.sfu.cmpt276.grow.with.you.controllers;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.enums.UserRole;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlantService plantService;

    @GetMapping("/profile")
    public String userProfile(Model model, HttpSession session) {
        User user = userService.getUserBySession(session);

        if (user == null) {
            return "redirect:main.html";
        }

        if (user.getRole() == UserRole.GROWER) {
            Grower grower = (Grower) user;
            model.addAttribute("user", grower);
            model.addAttribute("plants", plantService.getPlantsByGrower(grower));
        } else if (user.getRole() == UserRole.SPONSOR) {
            Sponsor sponsor = (Sponsor) user;
            model.addAttribute("user", (Sponsor) user);
            model.addAttribute("plants", plantService.getPlantsBySponsor(sponsor));
        } else {
            model.addAttribute("user", user);
        }

        return "protected/user/profile";
    }

}
