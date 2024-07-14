package ca.sfu.cmpt276.grow.with.you.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sfu.cmpt276.grow.with.you.models.MarketplaceRepository;
import ca.sfu.cmpt276.grow.with.you.models.Plant;

import java.util.List;

@Service
public class MarketplaceService {

    @Autowired
    private MarketplaceRepository marketplaceRepository;

    public Plant savePlant(Plant plant) {
        return marketplaceRepository.save(plant);
    }

    public Plant getPlantById(int id) {
        return marketplaceRepository.findById(id);
    }

    public List<Plant> getAllPlants() {
        return marketplaceRepository.findAll();
    }

    public List<Plant> findByName(String name) {
        return marketplaceRepository.findByName(name);
    }

    public List<Plant> findByCity(String city) {
        return marketplaceRepository.findByCity(city);
    }

    public List<Plant> findByPriceBetween(double minPrice, double maxPrice) {
        return marketplaceRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
