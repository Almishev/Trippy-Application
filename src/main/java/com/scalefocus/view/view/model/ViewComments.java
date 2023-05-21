package com.scalefocus.view.view.model;

import java.sql.Timestamp;

public class ViewComments {

    private String company;
    private String user;
    private String content;

    private double rating;

    private Timestamp date;

    public ViewComments() {
    }

    public ViewComments(String company, String user, String content, double rating, Timestamp date) {
        this.company = company;
        this.user = user;
        this.content = content;
        this.rating = rating;
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public String getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public double getRating() {
        return rating;
    }

    public Timestamp getDate() {
        return date;
    }
}
