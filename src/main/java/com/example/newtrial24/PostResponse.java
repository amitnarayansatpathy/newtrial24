package com.example.newtrial24;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class PostResponse {
    private int postID;
    private String postBody;
    //private LocalDate date;
    private Date date;
    private List<CommentResponse> comments;

    // Getters and setters

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
