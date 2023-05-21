package com.scalefocus.establishment;

public class Establishment {

    private int estId;
    private int categoryId;
    private String name;
    private int townId;

    public Establishment() {
    }

    public Establishment(int categoryId, String name, int townId) {
        this.categoryId = categoryId;
        this.name = name;
        this.townId = townId;
    }

    public Establishment(int estId, int categoryId, String name, int townId) {
        this.estId = estId;
        this.categoryId = categoryId;
        this.name = name;
        this.townId = townId;
    }

    public int getEstId() {
        return estId;
    }

    public void setEstId(int estId) {
        this.estId = estId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }


}
