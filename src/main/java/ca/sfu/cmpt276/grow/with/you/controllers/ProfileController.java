package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.Optional;

import ca.sfu.cmpt276.grow.with.you.models.Profile;
import ca.sfu.cmpt276.grow.with.you.models.ProfileRepository;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepo;

        @GetMapping("/profile")
        public String userProfile(Model model, HttpSession session) {
            Profile loggedInUser = (Profile) session.getAttribute("session_user");
            model.addAttribute("profile", loggedInUser);
            return "users/profile";
        }



        

    //NOT FOR ITERATION 1 
    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable int id, Model model) {
    Optional<Profile> optionalProfile = profileRepo.findById(id);
    if (optionalProfile.isPresent()) {
    Profile profile = optionalProfile.get();
    model.addAttribute("pr", profile);
    return "users/profile";
    } else
    return "redirect:/error";
    }

    @PostMapping("/users/edit/{id}")
    public String editRectangle(@PathVariable int id, HttpServletRequest request) {
        Optional<Profile> optionalProfile = profileRepo.findById(id);
        if (!optionalProfile.isPresent()) {
            return "redirect:/";
        }
        Profile profile = optionalProfile.get();

        String username = request.getParameter("username");
        String role = request.getParameter("role");
        String password = request.getParameter("password");

        profile.setUsername((username != null && !username.isEmpty()) ? username : profile.getUsername());
        profile.setRole((role != null && !role.isEmpty()) ? role : profile.getRole());
        profile.setPassword((password != null && !password.isEmpty()) ? password : profile.getPassword());

        profileRepo.save(profile);
        return "redirect:/";
    }
}
