package ca.sfu.cmpt276.grow.with.you.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Payment;
import ca.sfu.cmpt276.grow.with.you.models.PaymentRepository;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.PlantRepository;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.UserRepository;

@Service
public class PaymentService {

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    public Payment buyPlant(Sponsor user, int plantId) {
        Optional<Plant> plantOptional = plantRepository.findById(plantId);
        if (plantOptional.isEmpty()) {
            return null;
        }

        Plant plant = plantOptional.get();
        plant.setSponsor(user);
        plantRepository.save(plant);

        user.setBalance(user.getBalance() - plant.getPrice());
        userRepository.save(user);

        Payment payment = new Payment((Grower) plant.getGrower(), (Sponsor) user, plant, plant.getPrice(),
                LocalDate.now());
        paymentRepository.save(payment);

        return payment;
    }
}
