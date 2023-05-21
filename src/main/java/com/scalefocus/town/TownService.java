package com.scalefocus.town;

import com.scalefocus.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TownService {

    private final TownAccessor townAccessor;

    public TownService(TownAccessor townAccessor) {
        this.townAccessor = townAccessor;
    }


    public TownDto removeTown(int id) {

        Town oldTown = getTownById(id);
        townAccessor.deleteTown(id);
        return new TownDto(oldTown.getName());
    }

    public Town getTownById(int id) {
        return townAccessor.readTownById(id);
    }

    public List<Town> getAllTowns() {
        List<Town> towns ;
             towns = townAccessor.readAllTowns();

        return towns;
    }

    public Town addTown(TownRequest townRequest) {
        if (!townAccessor.isTownExist(townRequest.getName())) {
            Town town = new Town(townRequest.getName());
            town = townAccessor.addTown(town);
            return town;
        } else{
            throw new NotFoundException("This town already exist");
        }
    }

    public TownDto editTown(TownRequest townRequest, int id) {

        Town oldTown = getTownById(id);
        townAccessor.updateTown(new Town(id, townRequest.getName()));

        return new TownDto(oldTown.getName());
    }


}
