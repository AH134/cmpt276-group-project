package ca.sfu.cmpt276.grow.with.you.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    @ManyToOne
    @JoinColumn(name = "grower_id")
    private Grower grower;
    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;
    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;
    private double amount;
    private LocalDate paymentDate;

    public Payment() {
    }

    public Payment(Grower grower, Sponsor sponsor, Plant plant, double amount, LocalDate paymentDate) {
        this.grower = grower;
        this.sponsor = sponsor;
        this.plant = plant;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Grower getGrower() {
        return grower;
    }

    public void setGrower(Grower grower) {
        this.grower = grower;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public double isAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
