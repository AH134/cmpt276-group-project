package ca.sfu.cmpt276.grow.with.you.controllers;

import ca.sfu.cmpt276.grow.with.you.models.Payment;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.ChatService;
import ca.sfu.cmpt276.grow.with.you.services.PaymentService;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.setHttpHeader;
import ca.sfu.cmpt276.utils.enums.PaymentError;
import ca.sfu.cmpt276.utils.enums.UserRole;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MarketplaceController {

    @Autowired
    private PlantService plantService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/marketplace")
    public String getAllPlants(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "province", defaultValue = "all", required = false) String province,
            @RequestParam(value = "city", defaultValue = "all", required = false) String city,
            @RequestParam(value = "price", defaultValue = "all", required = false) String price, Model model,
            HttpSession session, HttpServletResponse res) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(res);
        if (user == null) {
            return "redirect:/main";
        }

        try {
            Page<Plant> plants = plantService.getAllPlantsByFilters(pageNo, pageSize, search, province, city, price);
            model.addAttribute("plants", plants.getContent());
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("province", province);
            model.addAttribute("city", city);
            model.addAttribute("price", price);
            model.addAttribute("hasPrevPage", plants.hasPrevious());
            model.addAttribute("hasNextPage", plants.hasNext());
            model.addAttribute("totalPages", plants.getTotalPages());

            return "protected/marketplace";
        } catch (Exception e) {
            return "redirect:/marketplace";
        }
    }

    @GetMapping("/marketplace/plant/{pid}")
    public String getonePlant(@PathVariable("pid") int pid, HttpServletResponse response, Model model,
            HttpSession session) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(response);
        if (user == null) {
            return "redirect:/main";
        }

        Plant plant = plantService.getPlantById(pid);
        if (plant == null) {
            return "redirect:/marketplace";
        }

        if (plant.getSponsor() != null) {
            return "redirect:/marketplace";
        }

        model.addAttribute("plant", plant);
        if (user != null) {
            model.addAttribute("isOwner", user.getUserId() == plant.getGrower().getUserId());
        }

        return "protected/viewPlant";
    }

    @PostMapping("/marketplace/plant/{pid}")
    public String buyPlant(@PathVariable("pid") int pid, HttpSession session, Model model,
            HttpServletResponse response) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(response);

        if (user.getRole() != UserRole.SPONSOR) {
            return "redirect:/main";

        }

        Plant plant = plantService.getPlantById(pid);
        if (user.getBalance() < plant.getPrice()) {
            PaymentError error = PaymentError.INSUFFICIENT_BALANCE;
            model.addAttribute("error", error.getMessage());
            model.addAttribute("plant", plant);
            model.addAttribute("isOwner", false);
            response.setStatus(error.getStatusCode());

            return "protected/viewPlant";
        }

        Payment payment = paymentService.buyPlant((Sponsor) user, pid);
        if (payment == null) {
            model.addAttribute("plant", plant);
            model.addAttribute("isOwner", false);
            return "protected/viewPlant";
        }

        chatService.createChat(plant, plant.getGrower(), (Sponsor) user);

        return "redirect:/plants/" + pid;
    }

}
