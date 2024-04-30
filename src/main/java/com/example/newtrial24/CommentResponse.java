package com.example.newtrial24;

import java.util.Date;

public class CommentResponse {
    private int commentID;
    private String commentBody;

    private CommentCreatorResponse commentCreator;

    // Getters and setters

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }


    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }



    public CommentCreatorResponse getCommentCreator() {
        return commentCreator;
    }

    public void setCommentCreator(CommentCreatorResponse commentCreator) {
        this.commentCreator = commentCreator;
    }
}

