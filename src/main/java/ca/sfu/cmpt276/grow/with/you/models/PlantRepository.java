package ca.sfu.cmpt276.grow.with.you.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Integer> {
    List<Plant> findByGrowerId(int growerId);

    List<Plant> findBySponsorId(int sponsorId);
}
