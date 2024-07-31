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
import org.springframework.web.multipart.MultipartFile;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.ImageUploadService;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.setHttpHeader;
import ca.sfu.cmpt276.utils.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class PlantController {

    @Autowired
    private PlantService plantService;

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private UserService userService;

    @GetMapping("/plants/add")
    public String getAddPlant(HttpServletRequest req,
            HttpServletResponse res, HttpSession session, Model model) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(res);
        if (user == null) {
            return "redirect:/";
        }

        if (user.getRole() != UserRole.GROWER) {
            return "redirect:/dashboard";
        }

        return "protected/user/addPlant";
    }

    @PostMapping("/plants/add")
    public String addPlant(@RequestParam Map<String, String> newPlant,
            @RequestParam("image") MultipartFile multipartFile, HttpServletResponse response,
            HttpServletRequest request,
            HttpSession session) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(response);
        try {
            String newName = newPlant.get("plantName");
            String newDesc = newPlant.get("plantDesc");
            Double newPrice = Double.valueOf(newPlant.get("price"));
            String newImg = imageUploadService.upload(multipartFile);

            String Province = newPlant.get("province");
            String City = Province + "-city";
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
            return "redirect:/plants";
        }
        return "redirect:/plants";

    }

    @GetMapping("/plants/edit/{pid}")
    public String getEditPlant(@PathVariable("pid") int pid,
            HttpServletResponse response, Model model,
            HttpSession session) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(response);
        if (user == null) {
            return "redirect:/";
        }

        if (user.getRole() != UserRole.GROWER) {
            return "redirect:/plants";
        }

        model.addAttribute("plant", plantService.getPlantById(pid));
        return "protected/user/editPlant.html";
    }

    @PostMapping("/plants/edit/{pid}")
    public String putPlant(@PathVariable("pid") int pid, @RequestParam Map<String, String> newPlant,
            @RequestParam("image") MultipartFile multipartFile,
            HttpSession session, HttpServletResponse response) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(response);
        try {
            String newName = newPlant.get("plantName");
            String newDesc = newPlant.get("plantDesc");
            Double newPrice = Double.valueOf(newPlant.get("price"));
            String newImg = "";
            if (!multipartFile.isEmpty()) {
                newImg = imageUploadService.upload(multipartFile);
            } else {
                newImg = plantService.getPlantById(pid).getImageUrl();
            }

            String Province = newPlant.get("province");
            String City = Province + "-city";
            String plantCity = newPlant.get(City);

            if (user.getRole() == UserRole.GROWER) {
                Grower grower = (Grower) user;
                Plant newP = new Plant(grower, null, newName, newDesc, newImg, newPrice, Province, plantCity);

                plantService.updatePlant(pid, newP);
                response.setStatus(201);
            }

        } catch (Exception e) {
            response.setStatus(400);
            // perhaps an error page later on
            return "redirect:/plants";
        }
        return "redirect:/plants";

    }

    // view one plant
    @GetMapping("/plants/{pid}")
    public String getOnePlant(@PathVariable("pid") int pid, HttpServletResponse response, Model model,
            HttpSession session) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(response);
        if (user == null) {
            return "redirect:/";
        }

        List<Plant> plantsList;
        if (user.getRole() == UserRole.GROWER) {
            plantsList = plantService.getPlantsByGrower((Grower) user);
            model.addAttribute("plants", plantsList);
            model.addAttribute("selectedPlant", plantService.getPlantById(pid));
            model.addAttribute("role", "grower");
            return "protected/user/growerPlants.html";
        }
        plantsList = plantService.getPlantsBySponsor((Sponsor) user);
        model.addAttribute("plants", plantsList);
        model.addAttribute("selectedPlant", plantService.getPlantById(pid));
        model.addAttribute("role", "sponsor");
        return "protected/user/sponsorPlants.html";
    }

    @GetMapping("/plants")
    public String getAllPlants(Model model, HttpSession session, HttpServletResponse res) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(res);
        if (user == null) {
            return "redirect:/";
        }

        List<Plant> plantsList;
        if (user.getRole() == UserRole.GROWER) {
            plantsList = plantService.getPlantsByGrower((Grower) user);
            model.addAttribute("plants", plantsList);
            if (plantsList.size() != 0) {
                model.addAttribute("selectedPlant", plantsList.get(0));
            }
            model.addAttribute("role", "grower");
            return "protected/user/growerPlants.html";
        } else if (user.getRole() == UserRole.SPONSOR) {
            plantsList = plantService.getPlantsBySponsor((Sponsor) user);
            model.addAttribute("plants", plantsList);
            if (plantsList.size() != 0) {
                model.addAttribute("selectedPlant", plantsList.get(0));
            }
            model.addAttribute("role", "sponsor");
            return "protected/user/sponsorPlants.html";
        }

        return "redirect:/dashboard";
    }

}