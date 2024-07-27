package ca.sfu.cmpt276.grow.with.you.models;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ReviewsRepository extends JpaRepository<Reviews, Integer>{

    List<Reviews> findByGrower(Grower grower);
    
}
