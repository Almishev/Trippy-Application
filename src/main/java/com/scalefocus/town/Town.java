package com.scalefocus.town;

import javax.validation.constraints.Pattern;

public class Town {

   private int townId;
 //  @Pattern(regexp = "[A-Za-z\\s]+", message = "Name must not be null or contain numbers")
   private String name;

    public Town() {
    }

    public Town(String name) {
        this.name = name;
    }

    public Town(int townId, String name) {
        this.townId = townId;
        this.name = name;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
