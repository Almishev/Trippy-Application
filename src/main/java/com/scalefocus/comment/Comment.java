package com.scalefocus.comment;

import java.sql.Timestamp;

public class Comment {

    private int commentId;
    private String content;
    private double rating;

    private int estId;
    private int userId;
    private Timestamp createdOn;
    private Timestamp editedOm;

    public Comment() {
    }

    public Comment(String content, double rating,int estId ,int userId) {
        this.content = content;
        this.rating = rating;
        this.estId=estId;
        this.userId = userId;
    }

    public Comment(String content, double rating, int estId, int userId, Timestamp createdOn, Timestamp editedOm) {
        this.content = content;
        this.rating = rating;
        this.estId = estId;
        this.userId = userId;
        this.createdOn = createdOn;
        this.editedOm = editedOm;
    }

    public Comment(int commentId, String content, double rating, int estId, int userId, Timestamp createdOn, Timestamp editedOm) {
        this.commentId = commentId;
        this.content = content;
        this.rating = rating;
        this.estId=estId;
        this.userId = userId;
        this.createdOn = createdOn;
        this.editedOm = editedOm;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getEstId() {
        return estId;
    }

    public void setEstId(int estId) {
        this.estId = estId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getEditedOm() {
        return editedOm;
    }

    public void setEditedOm(Timestamp editedOm) {
        this.editedOm = editedOm;
    }
}
