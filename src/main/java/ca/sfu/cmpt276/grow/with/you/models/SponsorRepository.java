package ca.sfu.cmpt276.grow.with.you.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

    @Query("SELECT g FROM Sponsor g ORDER BY g.plantsSponsored DESC")
    List<Sponsor> findTopSponsors();
}