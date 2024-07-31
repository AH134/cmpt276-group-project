package ca.sfu.cmpt276.grow.with.you.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notifId;
    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;
    @ManyToOne
    @JoinColumn(name = "grower_id")
    private Grower grower;
    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;
    private String message;

    public Notifications(){

    }

    public Notifications(Plant plant, Grower grower, String message){
        this.grower = grower;
        this.plant= plant;
        this.message = message;
    }

    public Notifications(Plant plant, Sponsor sponsor, String message){
        this.sponsor = sponsor;
        this.plant= plant;
        this.message = message;
    }

    public int getNotifId() {
        return notifId;
    }

    public void setNotifId(int notifId) {
        this.notifId = notifId;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
