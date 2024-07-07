package ca.sfu.cmpt276.grow.with.you.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "plant")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plantId;
    private int growerId;
    private int sponsorId;
    private String name;
    private String description;
    private double price;
    @Temporal(TemporalType.DATE)
    private Date paidDate;
    private String city;
    private String country;

    public Plant() {
    }

    public Plant(int growerId, String name, String description, double price, Date paidDate, String city,
            String country) {
        this.growerId = growerId;
        this.sponsorId = -1;
        this.name = name;
        this.description = description;
        this.price = price;
        this.paidDate = paidDate;
        this.city = city;
        this.country = country;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public int getGrowerId() {
        return growerId;
    }

    public void setGrowerId(int growerId) {
        this.growerId = growerId;
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
