package com.example.testtwitter.DAO;

public  class CreateCommentRequest {
    private String commentBody;
    private Long postID;
    private Long userID;
    // Getters and setters
    public String getCommentBody() {
        return commentBody;
    }



    public Long getPostID() {
        return postID;
    }


    public Long getUserID() {
        return userID;
    }


}

