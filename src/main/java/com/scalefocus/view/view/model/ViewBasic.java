package com.scalefocus.view.view.model;

public class ViewBasic {

    private String town;
    private String type;
    private String company;
    private double totalRating;

    public ViewBasic() {
    }

    public ViewBasic(String town,String type, String company, double totalRating) {
        this.town=town;
        this.type = type;
        this.company = company;
        this.totalRating = totalRating;
    }

    public String getTown() {
        return town;
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
