package ca.sfu.cmpt276.grow.with.you.models;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceRepository extends JpaRepository<Plant,Integer> {
    Plant save(Plant plant);
    Plant findById(int id);
    List<Plant> findAll();
    List<Plant> findByName(String name);
    List<Plant> findByCity(String city);
    List<Plant> findByPriceBetween(double minPrice, double maxPrice);
}
