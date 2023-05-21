package com.scalefocus.town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class TownController {

    private final TownService townService;

    @Autowired
    public TownController(TownService townService) {
        this.townService = townService;
    }

    @GetMapping("/towns")
    public ResponseEntity<List<Town>> getAllTowns() {
        List<Town> towns = townService.getAllTowns();
        return ResponseEntity.ok(towns);
    }

    @GetMapping("/towns/{id}")
    public ResponseEntity<Town> getTown(@PathVariable int id) {
        Town ourTown = townService.getTownById(id);
        return ResponseEntity.ok(ourTown);
    }

    @PostMapping("/towns")
    public ResponseEntity<Town> createTown(@RequestBody @Valid TownRequest townRequest) {
        Town town = townService.addTown(townRequest);

        URI location = UriComponentsBuilder.fromUriString("/towns/{townId}")
                .buildAndExpand(town.getTownId())
                .toUri();
        return ResponseEntity.created(location).build();
    }



    @DeleteMapping("/towns/{townId}")
    public ResponseEntity<TownDto> deleteItem(@PathVariable int townId,
                                              @RequestParam(required = false) boolean returnOld) {

        TownDto townDto = townService.removeTown(townId);
        if (returnOld) {
            return ResponseEntity.ok(townDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/towns/{id}")
    public ResponseEntity<TownDto> updateTown(
            @RequestBody @Valid TownRequest townRequest, @PathVariable int id,
            @RequestParam(required = false) boolean returnOld) {

        TownDto townDto = townService.editTown(townRequest, id);
        if (returnOld) {
            return ResponseEntity.ok(townDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
