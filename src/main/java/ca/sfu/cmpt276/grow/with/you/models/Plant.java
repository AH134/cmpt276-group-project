package ca.sfu.cmpt276.grow.with.you.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "plant")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plantId;
    @ManyToOne
    @JoinColumn(name = "grower_id")
    private Grower grower;
    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String province;
    private String city;
    @OneToMany(mappedBy = "plant")
    private List<Message> messages = new ArrayList<>();

    public Plant() {
    }

    public Plant(Grower grower, Sponsor sponsor, String name, String description, String imageUrl, double price,
            String province, String city) {
        this.grower = grower;
        this.sponsor = sponsor;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.province = province;
        this.city = city;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
