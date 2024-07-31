package ca.sfu.cmpt276.grow.with.you.models;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByGrower(Grower grower, Sort sort);

    List<Payment> findBySponsor(Sponsor sponsor, Sort sort);
}
