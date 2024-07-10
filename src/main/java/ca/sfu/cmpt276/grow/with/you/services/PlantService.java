package ca.sfu.cmpt276.grow.with.you.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.PlantRepository;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;

@Service
public class PlantService {
    @Autowired
    private PlantRepository plantRepository;

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

    public Plant createPlant(Plant plant) {
        return plantRepository.save(plant);
    }

    public void deletePlantById(int id) {
        plantRepository.deleteById(id);
    }

}
