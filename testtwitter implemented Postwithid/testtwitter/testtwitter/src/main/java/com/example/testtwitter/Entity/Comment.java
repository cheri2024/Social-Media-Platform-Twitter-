package com.example.testtwitter.Entity;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentBody;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    private Long userID;
    // Constructors, getters, and setters

    public Comment() {
    }

    public Comment(String commentBody, Post post,long UserID) {
        this.commentBody = commentBody;
        this.post = post;
        this.userID = UserID;
    }
    public Comment(long id, String commentBody, long UserID) {
        this.id =id;
        this.commentBody = commentBody;
        this.userID = UserID;
    }
    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
    public Long getId() {
        return id;
    }

    public Long getUserID() {
        return userID;
    }



}
