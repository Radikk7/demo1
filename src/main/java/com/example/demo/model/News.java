package com.example.demo.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @Column(length = 3000)
    private String description;
    private String category;
    private Timestamp timestamp;
    @ManyToOne
    private User user;
    private String filename;


    public void setUser(User user) {
        this.user = user;
    }

    public News(long id, String title, String description, String category, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.timestamp = timestamp;
    }
    public News(long id, String title, String description, String category, Timestamp timestamp , User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.timestamp = timestamp;
        this.user = user;
    }
    public News(long id, String title, String description, String category, Timestamp timestamp , User user,String filename) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.timestamp = timestamp;
        this.user = user;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public News(String title, String description, String category, Timestamp timestamp) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.timestamp = timestamp;
    }
    public News(String title, String description, String category, Timestamp timestamp,User user) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.timestamp = timestamp;
        this.user = user;
    }

    public News() {
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getUserName(){
        return user.getUsername();
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
