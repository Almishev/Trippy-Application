package com.scalefocus.establishment;

import com.scalefocus.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstablishService {

    private final EstablishmentAccessor estAccessor;

    @Autowired
    public EstablishService(EstablishmentAccessor estAccessor, EstablishmentMapper estMapper) {
        this.estAccessor = estAccessor;
    }

    public List<Establishment> getAllEstablishments(int limit) {
        List<Establishment> establishments = new ArrayList<>();
              establishments = estAccessor.readAllEstablishments(limit);

        return establishments;
    }


    public Establishment getEstablishmentById(int id) {
        return estAccessor.readEstablishmentById(id);
    }

    public Establishment addEstablish(EstRequest estRequest) {
        if(!estAccessor.isTheEstablishExist(estRequest.getCategoryId(),estRequest.getName(),estRequest.getTownId())) {
            Establishment establishment = new Establishment(0, estRequest.getCategoryId(), estRequest.getName(), estRequest.getTownId());
            establishment = estAccessor.addEstablishment(establishment);
            return establishment;
        } else {
            throw new NotFoundException("This establish already exist!");
        }
    }

    public EstDto editEst(EstRequest estRequest, int id) {

        Establishment oldEst = getEstablishmentById(id);
        estAccessor.updateEstablishment(new Establishment(id,estRequest.getCategoryId(), estRequest.getName(), estRequest.getTownId()));

        return new EstDto(oldEst.getCategoryId(),oldEst.getName(), oldEst.getTownId());
    }

    public EstDto removeEst(int id) {
        Establishment oldEst = getEstablishmentById(id);
        estAccessor.deleteEstablishment(id);
        return new EstDto(oldEst.getCategoryId(),oldEst.getName(), oldEst.getTownId());
    }

}
