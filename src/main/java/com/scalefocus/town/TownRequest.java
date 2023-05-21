package com.scalefocus.town;

import javax.validation.constraints.Pattern;

public class TownRequest {

    @Pattern(regexp = "[A-Za-z\\s]+", message = "Name must not be null or contain numbers")
    private String name;

    public TownRequest() {
    }

    public TownRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
