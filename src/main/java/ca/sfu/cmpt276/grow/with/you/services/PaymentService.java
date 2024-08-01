package ca.sfu.cmpt276.grow.with.you.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        plant.getGrower().setBalance(plant.getGrower().getBalance() + plant.getPrice());
        user.setPlantsSponsored(user.getPlantsSponsored() + 1);
        userRepository.save(user);

        Payment payment = new Payment((Grower) plant.getGrower(), (Sponsor) user, plant, plant.getPrice(),
                LocalDate.now());
        paymentRepository.save(payment);

        return payment;
    }

    public List<Payment> getPaymentByGrower(Grower grower) {
        return paymentRepository.findByGrower(grower, Sort.by(Sort.Direction.DESC, "paymentId"));
    }

    public List<Payment> getPaymentBySponsor(Sponsor sponsor) {
        return paymentRepository.findBySponsor(sponsor, Sort.by(Sort.Direction.DESC, "paymentId"));
    }

    public void deletePaymentByPlant(Plant plant) {
        Optional<Payment> pOptional = paymentRepository.findByPlant(plant);
        if (pOptional.isPresent()) {
            Payment payment = pOptional.get();
            paymentRepository.deleteById(payment.getPaymentId());
        }
    }
}
