package ca.sfu.cmpt276.grow.with.you.models;

import jakarta.persistence.*;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String username;
    private String password;
    private String role;
    private double balance;
    private int posts;
    private int plants;

    public Profile() {}

    public Profile(String username, String password, String role, double balance, int posts, int plants) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = balance;
        this.posts = posts;
        this.plants = plants;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public int getPosts() {
        return posts;
    }
    public void setPosts(int posts) {
        this.posts = posts;
    }
    public int getPlants() {
        return plants;
    }
    public void setPlants(int plants) {
        this.plants = plants;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
}
