package ca.sfu.cmpt276.grow.with.you.models;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.cmpt276.utils.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sponsor")
public class Sponsor extends User {
    private int plantsSponsored;
    private double totalSpent;
    @OneToMany(mappedBy = "sponsor")
    private List<Plant> sponsoredPlants = new ArrayList<>();
    @OneToMany(mappedBy = "sponsor")
    private List<Payment> payments = new ArrayList<>();

    public Sponsor() {

    }

    public Sponsor(String username, String password, String email, double balance, int plantsSponsored,
            double totalSpent) {
        super(username, password, email, UserRole.SPONSOR, balance);
        this.plantsSponsored = plantsSponsored;
        this.totalSpent = totalSpent;
    }

    public int getPlantsSponsored() {
        return plantsSponsored;
    }

    public void setPlantsSponsored(int plantsSponsored) {
        this.plantsSponsored = plantsSponsored;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public List<Plant> getSponsorePlants() {
        return sponsoredPlants;
    }

    public void setSponsoredPlants(List<Plant> sponsoredPlants) {
        this.sponsoredPlants = sponsoredPlants;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
