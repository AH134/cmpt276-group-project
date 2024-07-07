package ca.sfu.cmpt276.grow.with.you.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String username;
    private String password;
    private int role;
    private String email;
    private double balance;
    private boolean isAdmin;
    @OneToOne(mappedBy = "user")
    private Profile profile;

    public User() {
    }

    public User(String username, String password, int role, String email, double balance, boolean isAdmin,
            Profile profile) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.balance = balance;
        this.isAdmin = isAdmin;
        this.profile = profile;
    }

    public int getUid() {
        return uid;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public boolean getIsAdmin() {
        return isAdmin;

    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
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

    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}