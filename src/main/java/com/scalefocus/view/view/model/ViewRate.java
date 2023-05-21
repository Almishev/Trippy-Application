package com.scalefocus.view.view.model;

public class ViewRate {

    private String type;
    private String company;
    private double rate;

    public ViewRate() {
    }

    public ViewRate(String type, String company, double rate) {
        this.type = type;
        this.company = company;
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public String getCompany() {
        return company;
    }

    public double getRating() {
        return rate;
    }

}
