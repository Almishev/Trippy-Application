package com.scalefocus.view;

import com.scalefocus.exception.NotFoundException;
import com.scalefocus.view.view.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewService {
    private final ViewAccessor viewAccessor;

    public ViewService(ViewAccessor viewAccessor) {
        this.viewAccessor = viewAccessor;

    }

    public List<ViewBasic> getAllViews(int limit) {

       List<ViewBasic> getAll =  viewAccessor.readAllViews(limit);
       if(getAll.isEmpty()){
           throw new NotFoundException(" There are nothing here.");
       }
        return getAll;
    }

    public List<ViewCount> getAllCounts(){
        return viewAccessor.readAllCounts();
    }

    public List<ViewRate> getAllViewsFromTownAndRate(String town, double rate) {

        List<ViewRate> getAll = viewAccessor.showBusinessFromTownAndByRate(town,rate);
        if(getAll.isEmpty()){
            throw new NotFoundException("There are nothing here "+town+ " and rate "+rate);
        }
        return getAll;

    }

    public List<ViewTown> readAllViewsFromTownName(String town) {
        List<ViewTown> readAllViewFromTown ;

        readAllViewFromTown= viewAccessor.readAllViewsFromTownName(town);
        if(readAllViewFromTown.isEmpty()){
            throw new NotFoundException("There are nothing for town "+town);
        } else {
            return readAllViewFromTown;
        }
    }

    public List<ViewType> readAllViewTypes(String type,int low, int high) {
        List<ViewType>  readAllVandT ;

       readAllVandT = viewAccessor.readAllViewsForType(type,low,high);
        if (readAllVandT.isEmpty()){
            throw new NotFoundException("There are nothing with type: "+type);
        }
       return readAllVandT;
    }

    public List<ViewComments> readAllViewCommentsByEstablishmentName(String name) {
        List<ViewComments> viewCommentsList ;

        viewCommentsList =  viewAccessor.readComments(name);
        if (viewCommentsList.isEmpty()){
            throw new NotFoundException("There are nothing here for : "+name);
        }
        return viewCommentsList;

    }



}
