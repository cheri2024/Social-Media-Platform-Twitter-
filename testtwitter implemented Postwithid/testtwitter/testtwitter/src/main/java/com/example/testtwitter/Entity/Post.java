package com.example.testtwitter.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postBody;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    // Constructors, getters, and setters
    // Static variable to store the max value of post ID
    private static Long maxPostId = Long.MIN_VALUE;
    public Post() {
    }
    private long userID;
    public Post(String postBody, LocalDate date, User user) {
        this.postBody = postBody;
        this.date = date;
        this.user = user;
        updateMaxPostId();
    }

    public Long getId() {
        return id;
    }
    public void setUserid(long userid) {userID = user.getId();}
    public long getUserid()
    {
        return userID;
    }


    public LocalDate getDate() {
        return date;
    }
    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
    public User getUser() {
        return user;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static Long getMaxPostId() {
        return maxPostId;
    }

    private void updateMaxPostId() {
        if (this.id != null && this.id > maxPostId) {
            maxPostId = this.id;
        }
    }
}
