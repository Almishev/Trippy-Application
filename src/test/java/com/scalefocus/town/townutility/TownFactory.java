package com.scalefocus.town.townutility;

import com.scalefocus.town.Town;
import com.scalefocus.town.TownDto;
import com.scalefocus.town.TownRequest;

import static com.scalefocus.town.townutility.TownConstants.TOWN_ID;
import static com.scalefocus.town.townutility.TownConstants.TOWN_NAME;

public final class TownFactory {

    private TownFactory() {
        throw new IllegalStateException();
    }

    public static Town getDefaultTown(){
        return new Town(TOWN_ID, TOWN_NAME);
    }

    public static TownDto getDefaultTownDto(){
        return new TownDto(TOWN_NAME);
    }

    public static TownRequest getDefaultTownRequest(){
        return new TownRequest(TOWN_NAME);
    }

}
