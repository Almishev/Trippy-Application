package com.scalefocus.comment;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class CommentRequest {

    private String content;

    @Min(value = 1, message = "Rate should not be less than 1")
    @Max(value = 5, message = "Rate should not be greater than 6")
    private double rating;
    private int estId;
    private int userId;

    public CommentRequest() {
    }

    public CommentRequest(String content, double rating, int estId, int userId) {
        this.content = content;
        this.rating = rating;
        this.estId = estId;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public double getRating() {
        return rating;
    }

    public int getEstId() {
        return estId;
    }

    public int getUserId() {
        return userId;
    }
}
