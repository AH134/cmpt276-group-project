package ca.sfu.cmpt276.grow.with.you.models;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.cmpt276.utils.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "grower")
public class Grower extends User {
    private int plantsGrown;
    private int reviewScore;
    private double totalIncome;
    @OneToMany(mappedBy = "grower")
    private List<Plant> listedPlants = new ArrayList<>();
    @OneToMany(mappedBy = "sponsor")
    private List<Payment> payments = new ArrayList<>();

    public Grower() {
    }

    public Grower(String username, String password, String email, double balance, int plantsGrown,
            int reviewScore,
            double totalIncome) {
        super(username, password, email, UserRole.GROWER, balance);
        this.plantsGrown = plantsGrown;
        this.reviewScore = reviewScore;
        this.totalIncome = totalIncome;
    }

    public int getPlantsGrown() {
        return plantsGrown;
    }

    public void setPlantsGrown(int plantsGrown) {
        this.plantsGrown = plantsGrown;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public List<Plant> getListedPlants() {
        return listedPlants;
    }

    public void setListedPlants(List<Plant> listedPlants) {
        this.listedPlants = listedPlants;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

}
