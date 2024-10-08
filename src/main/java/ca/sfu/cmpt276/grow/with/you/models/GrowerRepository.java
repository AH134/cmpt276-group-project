package ca.sfu.cmpt276.grow.with.you.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GrowerRepository extends JpaRepository<Grower, Integer> {

    @Query("SELECT g FROM Grower g ORDER BY g.plantsGrown DESC")
    List<Grower> findTopGrowers();
}
 