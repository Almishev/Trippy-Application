package com.scalefocus.establishment;

public class EstDto {

    private int categoryId;
    private String name;
    private int townId;

    public EstDto() {
    }

    public EstDto(int categoryId, String name, int townId) {
        this.categoryId = categoryId;
        this.name = name;
        this.townId = townId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public int getTownId() {
        return townId;
    }
}
