package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class PlantController {

    @Autowired
    private PlantService plantService;

    @Autowired
    private UserService userService;

    @GetMapping("/plants/add")
    public String getAddPlant(HttpServletRequest req,
            HttpServletResponse res, HttpSession session, Model model) {
        User user = userService.getUserBySession(session);
        if (user == null) {
            return "redirect:/login";
        }

        if (user.getRole() != UserRole.GROWER) {
            return "redirect:/dashboard";
        }

        return "protected/user/addPlant";
    }

    @PostMapping("/plants/add")
    public String addPlant(@RequestParam Map<String, String> newPlant, HttpServletResponse response,
            HttpServletRequest request,
            HttpSession session) {
        User user = userService.getUserBySession(session);
        try {
            String newName = newPlant.get("plantName");
            String newDesc = newPlant.get("plantDesc");
            Double newPrice = Double.parseDouble(newPlant.get("price"));
            String newImg = newPlant.get("image");

            String Province = newPlant.get("province");
            String City = newPlant.get(Province + "-city");

            if (user.getRole() == UserRole.GROWER) {
                Grower grower = (Grower) user;
                Plant newP = new Plant(grower, null, newName, newDesc, newImg, newPrice, Province, City);

                plantService.createPlant(newP, grower);
            }

        } catch (Exception e) {
            response.setStatus(400);
            // perhaps an error page later on
            return "forward:/addPlant.html";
        }
        return "redirect:/dashboard";

    }

    // view one plant
    @GetMapping("/plants/view/{pid}")
    public String getMethodName(@PathVariable("pid") int pid, HttpServletResponse response, Model model) {
        Plant plant = plantService.getPlantById(pid);

        model.addAttribute("plant", plant);
        return new String();
    }

    // edit a plant
}