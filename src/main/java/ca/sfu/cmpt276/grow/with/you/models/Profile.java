package ca.sfu.cmpt276.grow.with.you.models;

import jakarta.persistence.*;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    @OneToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;
    private int posts;
    private int plants;

    public Profile() {
    }

    public Profile(User user, int posts, int plants) {
        this.user = user;
        this.posts = posts;
        this.plants = plants;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}