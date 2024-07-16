package ca.sfu.cmpt276.grow.with.you.controllers;

import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MarketplaceController {

    @Autowired
    private PlantService plantService;

    @GetMapping("/marketplace")
    public String getAllPlants(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "province", defaultValue = "all", required = false) String province,
            @RequestParam(value = "city", defaultValue = "all", required = false) String city,
            @RequestParam(value = "price", defaultValue = "all", required = false) String price, Model model) {

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

            return "marketplace";
        } catch (Exception e) {
            return "redirect:/marketplace";
        }
    }

}
