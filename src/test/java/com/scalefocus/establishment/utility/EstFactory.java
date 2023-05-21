package com.scalefocus.establishment.utility;


import com.scalefocus.establishment.EstDto;
import com.scalefocus.establishment.EstRequest;
import com.scalefocus.establishment.Establishment;
import org.checkerframework.checker.units.qual.C;

import static com.scalefocus.establishment.utility.EstConstants.*;

public final class EstFactory {

    private EstFactory() {
        throw new IllegalStateException();
    }

    public static Establishment getDefaultEst(){
        return new Establishment(EST_ID,CATEGORY_ID,NAME,TOWN_Id);
    }

    public static EstDto getDefaultEstDto(){
        return new EstDto(CATEGORY_ID,NAME,TOWN_Id);
    }

    public static EstRequest getDefaultEstRequest(){
        return new EstRequest(CATEGORY_ID,NAME,TOWN_Id);
    }
}


