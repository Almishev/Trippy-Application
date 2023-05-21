package com.scalefocus.establishment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class EstablishController {

    private final EstablishService estService;

    @Autowired
    public EstablishController(EstablishService estService) {
        this.estService = estService;
    }

    @GetMapping("/ests")
    public ResponseEntity<List<Establishment>> getAllEstablishments(@RequestParam(name = "limit", defaultValue = "100000") int limit) {
        List<Establishment> establishments = estService.getAllEstablishments(limit);
        return ResponseEntity.ok(establishments);
    }


    @GetMapping("/ests/{estId}")
    public ResponseEntity<Establishment> getEstablishment(@PathVariable int estId) {
        Establishment est = estService.getEstablishmentById(estId);
        EstDto itemDto = new EstDto(est.getCategoryId(),est.getName(), est.getTownId());

        return ResponseEntity.ok(est);
    }

    @PostMapping("/ests")
    public ResponseEntity<Establishment> createEstablishment(@RequestBody @Valid EstRequest estRequest) {
        Establishment establishment = estService.addEstablish(estRequest);

        URI location = UriComponentsBuilder.fromUriString("/ests/{estId}")
                .buildAndExpand(establishment.getEstId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @PutMapping("/ests/{estId}")
    public ResponseEntity<EstDto> updateEstablishment(
            @RequestBody @Valid EstRequest estRequest, @PathVariable int estId,
            @RequestParam(required = false) boolean returnOld) {

        EstDto estDto = estService.editEst(estRequest, estId);
        if (returnOld) {
            return ResponseEntity.ok(estDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @DeleteMapping("/ests/{id}")
    public ResponseEntity<EstDto> deleteEstablishment(@PathVariable int id,
                                              @RequestParam(required = false) boolean returnOld) {

        EstDto estDto = estService.removeEst(id);
        if (returnOld) {
            return ResponseEntity.ok(estDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
