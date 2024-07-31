package ca.sfu.cmpt276.grow.with.you.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    List<Chat> findByPlant(Plant plant);

    List<Chat> findByGrower(Grower grower);

    List<Chat> findBySponsor(Sponsor sponsor);
}
