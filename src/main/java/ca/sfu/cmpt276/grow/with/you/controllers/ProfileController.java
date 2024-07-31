package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.enums.UserError;
import ca.sfu.cmpt276.utils.enums.UserRole;
import ca.sfu.cmpt276.utils.setHttpHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlantService plantService;

    @GetMapping("/profile")
    public String userProfile(Model model, HttpSession session, HttpServletResponse res) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(res);

        if (user == null) {
            return "redirect:/";
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

    @GetMapping("/profile/edit")
    public String getEditProfile(Model model, HttpSession session, HttpServletResponse res) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(res);

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        return "protected/user/edit";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam Map<String, String> formInput, HttpSession session, HttpServletResponse res,
            HttpServletRequest req, Model model){
        try{
            setHttpHeader.setHeader(res);
            User user = (User) session.getAttribute("session_user");
            String newName = formInput.get("username");
            User userByName = userService.getUserByUsername(newName);
            UserError nameError = UserError.USERNAME_TAKEN;

            if (userByName != null) {
                model.addAttribute("user", formInput);
                model.addAttribute("error1", nameError.getMessage());
                return "protected/user/edit";
            }
            else{
                user.setUsername(newName);
                userService.createUser(user);
                return "redirect:/profile";
            }
        }
        catch(Exception e){
            res.setStatus(400);
            return "protected/user/profile";
        }
    }
}
