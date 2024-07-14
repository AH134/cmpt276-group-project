package ca.sfu.cmpt276.grow.with.you.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Integer> {
    List<Plant> findByGrower(Grower grower);

    List<Plant> findBySponsor(Sponsor sponsor);
}
