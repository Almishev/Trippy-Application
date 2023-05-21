package com.scalefocus.establishment;

import javax.validation.constraints.Pattern;

public class EstRequest {

    private int categoryId;
    @Pattern(regexp = "[A-Za-z\\s]+", message = "Name must not be null or contain numbers")
    private String name;

    private int townId;


    public EstRequest(){}

    public EstRequest(int categoryId, String name, int townId) {
        this.categoryId = categoryId;
        this.name = name;
        this.townId = townId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getTownId() {
        return townId;
    }

    public String getName() {
        return name;
    }



}
