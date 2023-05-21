package com.scalefocus.view.view.model;

public class ViewType {

    private String town;
    private String name;
    private double rating;

    public ViewType(String town, String name, double rating) {
        this.town = town;
        this.name = name;
        this.rating = rating;
    }

    public String getTown() {
        return town;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "ViewType{" +
                "town='" + town + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}
