package ca.sfu.cmpt276.grow.with.you.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatId;
    @ManyToOne
    private Sponsor sponsor;
    @ManyToOne
    private Grower grower;
    @OneToOne
    private Plant plant;
    @OneToMany(mappedBy = "chat")
    private List<ChatMessage> messages = new ArrayList<>();

    public Chat() {
    }

    public Chat(Sponsor sponsor, Grower grower, Plant plant) {
        this.sponsor = sponsor;
        this.grower = grower;
        this.plant = plant;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
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

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessage(List<ChatMessage> messages) {
        this.messages = messages;
    }

}