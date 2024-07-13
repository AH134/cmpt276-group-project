package ca.sfu.cmpt276.grow.with.you.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.service.MarketplaceService;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class MarketplaceController {

    @Autowired
    private MarketplaceService marketplaceService;

    @GetMapping
    public List<Plant> getAllPlants() {
        return marketplaceService.getAllPlants();
    }

    @PostMapping
    public String savePlant(@ModelAttribute Plant plant) {
        marketplaceService.savePlant(plant);
        return "redirect:/marketplace";
    }

    @GetMapping("/{id}")
    public Plant getPlantById(@PathVariable int id) {
        return marketplaceService.getPlantById(id);
    }

    @GetMapping("/search")
    public List<Plant> searchPlants(@RequestParam String query) {
        return marketplaceService.findByName(query);
    }

    @GetMapping("/filter")
    public List<Plant> filterPlants(@RequestParam(required = false) String city,
                                    @RequestParam(required = false) Double minPrice,
                                    @RequestParam(required = false) Double maxPrice) {
        List<Plant> plants = marketplaceService.getAllPlants();

        if (city != null && !city.isEmpty()) 
            plants = marketplaceService.findByCity(city);
        
        if (minPrice != null && maxPrice != null) 
            plants = marketplaceService.findByPriceBetween(minPrice, maxPrice);

        return plants;
    }
}

