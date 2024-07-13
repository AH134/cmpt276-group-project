package ca.sfu.cmpt276.grow.with.you.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.service.MarketplaceService;

import java.util.List;

@Controller
@RequestMapping("/marketplace")
public class MarketplaceWeb {

    @Autowired
    private MarketplaceService marketplaceService;

    @GetMapping
    public String getMarketplacePage(Model model) {
        List<Plant> plants = marketplaceService.getAllPlants();
        model.addAttribute("plants", plants);
        return "marketplace";
    }
}
