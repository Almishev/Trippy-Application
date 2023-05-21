package com.scalefocus.view.view.model;

public class ViewTown {


    private String type;
    private String company;
    private double totalRating;

    private int totalComments;

    public ViewTown() {
    }

    public ViewTown(String type, String company, double totalRating,int totalComments) {
        this.type = type;
        this.company = company;
        this.totalRating = totalRating;
        this.totalComments=totalComments;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public String getType() {
        return type;
    }

    public String getCompany() {
        return company;
    }

    public double getTotalRating() {
        return totalRating;
    }



}
