package ca.sfu.cmpt276.grow.with.you.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.PlantRepository;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.UserRepository;

@Service
public class PlantService {
    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    public Plant getPlantById(int id) {
        Optional<Plant> plant = plantRepository.findById(id);
        return plant.orElse(null);
    }

    public List<Plant> getPlantsByGrower(Grower grower) {
        return plantRepository.findByGrower(grower);
    }

    public List<Plant> getPlantsBySponsor(Sponsor sponsor) {
        return plantRepository.findBySponsor(sponsor);
    }

    public Page<Plant> getAllPlantsPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Plant> plants = plantRepository.findAll(pageable);

        return plants;
    }

    public Page<Plant> getAllPlantsByFilters(int pageNo, int pageSize, String name, String province, String city,
            String price) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        name = name.equalsIgnoreCase("") ? null : name;
        province = province.equalsIgnoreCase("all") ? null : province;
        city = city.equalsIgnoreCase("all") ? null : city;
        Optional<Integer> minPrice;
        Optional<Integer> maxPrice;
        if (price.equalsIgnoreCase("all")) {
            minPrice = null;
            maxPrice = null;
        } else {
            String[] priceRange = price.split("-");
            minPrice = Optional.of(Integer.parseInt(priceRange[0]));
            maxPrice = Optional.of(Integer.parseInt(priceRange[1]));
        }

        Page<Plant> plants = plantRepository.findAllByFilters(name, province, city, minPrice, maxPrice, pageable);

        return plants;
    }

    public Plant createPlant(Plant plant, Grower grower) {
        grower.setPlantsGrown(grower.getPlantsGrown() + 1);
        userRepository.save(grower);
        return plantRepository.save(plant);
    }

    public void deletePlantById(int id) {
        plantRepository.deleteById(id);
    }

}
