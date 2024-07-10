package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sfu.cmpt276.grow.with.you.models.PlantRepository;
import ca.sfu.cmpt276.grow.with.you.models.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class PlantController {
    @Autowired
    private PlantRepository plantRepo;

    @Autowired
    private UserRepository userRepo;

    //getmapping for one plant profile
    @GetMapping("/add/newPlant")
    public String getMethodName(@RequestParam Map<String,String> newPlant, HttpServletResponse response, HttpServletRequest request,
    Model model){
        try{
            String newName = newPlant.get("plantName");
            String newDesc = newPlant.get("plantDesc");
            Double newPrice = Double.parseDouble(newPlant.get("price"));

            //todo: change id to province
            String Province = newPlant.get("country");
            String City = newPlant.get("city");

        }
        catch(Exception e){

        }
        return "weh";
        
    }
    

    //postmapping for creating a plant

    //edit a plant
}
