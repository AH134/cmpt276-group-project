package ca.sfu.cmpt276.grow.with.you.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.NotificationRepository;
import ca.sfu.cmpt276.grow.with.you.models.Notifications;
import ca.sfu.cmpt276.grow.with.you.models.PlantRepository;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.UserRepository;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.User;

@Service
public class NotificationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    NotificationRepository notifRepository;

    public Notifications createNotifSponsor(int sponsor_id, int plantId){
        String sponsorBuy = "You recently bought a plant:";

        Optional<User> sponsorOptional = userRepository.findById(sponsor_id);
        Optional<Plant> plantOptional = plantRepository.findById(plantId);

        Plant plant = plantOptional.get();
        Sponsor sponsor = (Sponsor) sponsorOptional.get();

        Notifications notif = new Notifications();
        notif.setMessage(sponsorBuy);
        notif.setPlant(plant);
        notif.setSponsor(sponsor);

        return notifRepository.save(notif);
    }

    public Notifications createNotifGrower(int grower_id, int plantId){
        String growerBuy = "Your plant was recently bought:";

        Optional<User> growerOptional = userRepository.findById(grower_id);
        Optional<Plant> plantOptional = plantRepository.findById(plantId);

        Grower grower = (Grower) growerOptional.get();
        Plant plant = plantOptional.get();

        Notifications notif = new Notifications();
        notif.setMessage(growerBuy);
        notif.setPlant(plant);
        notif.setGrower(grower);

        return notifRepository.save(notif);
    }

    public List<Notifications> getAllNotifsGrower(Grower grower){
        return notifRepository.findByGrower(grower);
    }

    public List<Notifications> getAllNotifsSponsor(Sponsor sponsor){
        return notifRepository.findBySponsor(sponsor);
    }
    
}
