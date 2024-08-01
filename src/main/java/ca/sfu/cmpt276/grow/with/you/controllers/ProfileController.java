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
        return "protected/user/edit.html";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam Map<String, String> formInput, HttpSession session, HttpServletResponse res,
            HttpServletRequest req, Model model){
        try{
            setHttpHeader.setHeader(res);
            User user = (User) session.getAttribute("session_user");
            String newName = formInput.get("username");
            String newEmail = formInput.get("email");
            User userByName = userService.getUserByUsername(newName);
            User userByEmail = userService.getUserByEmail(newEmail);
            UserError nameError = UserError.USERNAME_TAKEN;
            UserError emailError = UserError.EMAIL_TAKEN;

            if (user.getUsername().equals(newName) && user.getEmail().equals(newEmail)) {
                model.addAttribute("user", user);
                return "redirect:/profile";
            }
            
            model.addAttribute("error1", "");
            model.addAttribute("error2", "");
            if(!user.getUsername().equals(newName) || !user.getEmail().equals(newEmail)) { 
                if ((userByName != null) && !(user.getUsername().equals(newName))) {
                    System.out.println("error1");
                    model.addAttribute("error1", nameError.getMessage());
                }
                
                if (userByEmail != null && !(user.getEmail().equals(newEmail))) {
                    System.out.println("error2");
                    model.addAttribute("error2", emailError.getMessage());
                }

                if (model.getAttribute("error1") != "" || model.getAttribute("error2") != "") {
                    model.addAttribute("user", formInput);
                    return "protected/user/edit.html";
                }
            }
            user.setUsername(newName);
            user.setEmail(newEmail);
            userService.createUser(user);
            model.addAttribute("user", user);
            return "redirect:/profile";
        }
        catch(Exception e){
            res.setStatus(400);
            return "protected/user/profile.html";
        }
    }
}
