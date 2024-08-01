package ca.sfu.cmpt276.grow.with.you.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notifications, Integer> {
    List<Notifications> findByGrower(Grower grower);

    List<Notifications> findBySponsor(Sponsor sponsor);

    List<Notifications> findByPlant(Plant plant);
}
