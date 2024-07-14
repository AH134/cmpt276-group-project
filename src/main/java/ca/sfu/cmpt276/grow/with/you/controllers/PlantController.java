package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
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
        return "redirect:/dashboard";

    }
    
    @GetMapping("/plants/view")
    public String viewPlants(){
        return "/viewPlant";
    }

    // view one plant
    @GetMapping("/plants/view/{pid}")
    public String getMethodName(@PathVariable("pid") int pid, HttpServletResponse response, Model model) {
        Plant plant = plantService.getPlantById(pid);

        model.addAttribute("plant", plant);
        return "/viewPlant";
    }

    // edit a plant
    //check that they have permissions to edit the plant
    @GetMapping("/plants/edit/{pid}")
    public String getMethodName(@PathVariable("pid") int pid, Model model, HttpSession session, HttpServletResponse reponse) {
        User user = userService.getUserBySession(session);
        Plant plant = plantService.getPlantById(pid);

        if(plant.getGrower().getUserId() == user.getUserId()){
            //return to the edit page
            model.addAttribute("plant", plant);
        }

        return "redirect:/dashboard";
    }

    //editing the plant
    @PutMapping("plants/edit/{pid}")
    public String putMethodName(@PathVariable("pid") Integer pid, @RequestParam Map<String, String> changedPlant, Model model,
    HttpServletResponse response, HttpSession session) {

        User user = userService.getUserBySession(session);
        try {
            String newName = changedPlant.get("plantName");
            String newDesc = changedPlant.get("plantDesc");
            Double newPrice = Double.valueOf(changedPlant.get("price"));
            String newImg = changedPlant.get("image");

            String Province = changedPlant.get("province");
            String City = Province+"-city";
            String plantCity = changedPlant.get(City);

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

        
        return "/plants/view/{id}";
    }
    
}