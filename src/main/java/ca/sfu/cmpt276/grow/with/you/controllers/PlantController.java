package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        System.out.println(user.getRole());

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
            Double newPrice = Double.valueOf(newPlant.get("price"));
            String newImg = newPlant.get("image");

            String Province = newPlant.get("province");
            String City = Province+"-city";
            String plantCity = newPlant.get(City);

            if (user.getRole() == UserRole.GROWER) {
                Grower grower = (Grower) user;
                Plant newP = new Plant(grower, null, newName, newDesc, newImg, newPrice, Province, plantCity);

                plantService.createPlant(newP, grower);
                response.setStatus(201);
            }

        } catch (Exception e) {
            response.setStatus(400);
            // perhaps an error page later on
            return "redirect:/dashboard";
        }
        return "redirect:/plants";

    }
    
    @GetMapping("/plants/view")
    public String viewPlants(){
        return "/viewPlant";
    }

    // view one plant
    @GetMapping("/plants/{pid}")
    public String getMethodName(@PathVariable("pid") int pid, HttpServletResponse response, Model model,
            HttpSession session) {
        User user = userService.getUserBySession(session);
        if (user == null) {
            return "redirect:/login";
        }

        List<Plant> plantsList;
        if (user.getRole() == UserRole.GROWER) {
            plantsList = plantService.getPlantsByGrower((Grower) user);
            model.addAttribute("plants", plantsList);
            model.addAttribute("selectedPlant", plantService.getPlantById(pid));
        } else if (user.getRole() == UserRole.SPONSOR) {
            plantsList = plantService.getPlantsBySponsor((Sponsor) user);
            model.addAttribute("plants", plantsList);
            model.addAttribute("selectedPlant", plantService.getPlantById(pid));
        }

        return "protected/user/growerPlants.html";
    }

    @GetMapping("/plants")
    public String getAllPlants(Model model, HttpSession session) {
        User user = userService.getUserBySession(session);
        if (user == null) {
            return "redirect:/login";
        }

        List<Plant> plantsList;
        if (user.getRole() == UserRole.GROWER) {
            plantsList = plantService.getPlantsByGrower((Grower) user);
            model.addAttribute("plants", plantsList);
            if (plantsList.size() != 0) {
                model.addAttribute("selectedPlant", plantsList.get(0));
            }
            return "protected/user/growerPlants.html";
        } else if (user.getRole() == UserRole.SPONSOR) {
            plantsList = plantService.getPlantsBySponsor((Sponsor) user);
            model.addAttribute("plants", plantsList);
            if (plantsList.size() != 0) {
                model.addAttribute("selectedPlant", plantsList.get(0));
            }
            return "protected/user/sponsorPlants.html";
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/public/plants/{pid}")
    public String getonePlant(@PathVariable("pid") int pid, HttpServletResponse response, Model model,
    HttpSession session) {
        User user = userService.getUserBySession(session);
        Plant plant = plantService.getPlantById(pid);

        model.addAttribute("plant", plant);

        return "protected/user/viewPlant";
    }
}