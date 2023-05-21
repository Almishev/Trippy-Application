package com.scalefocus.view;

import com.scalefocus.town.Town;
import com.scalefocus.view.view.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ViewController {

    private final ViewService viewService;

    @Autowired
    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    @GetMapping("/views")
    public ResponseEntity<List<ViewBasic>> getAllViews(@RequestParam(name = "limit", defaultValue = "100000") int limit) {
        List<ViewBasic> views = viewService.getAllViews(limit);
        return ResponseEntity.ok(views);
    }

    @GetMapping("/rate")
    public ResponseEntity<List<ViewRate>> getAllViewsFromTownAndRate(@RequestParam String town, @RequestParam double rate) {
        List<ViewRate> views = viewService.getAllViewsFromTownAndRate(town,rate);
        return ResponseEntity.ok(views);
    }

    @GetMapping("/view/v1")
    public ResponseEntity<List<ViewTown>> getViewByTown(@RequestParam String town) {
        List<ViewTown> ourView = viewService.readAllViewsFromTownName(town);
        return ResponseEntity.ok(ourView);
    }


    @GetMapping("/view/v2")
    public ResponseEntity<List<ViewType>> getViewByType(@RequestParam String type,@RequestParam(name = "lowest", defaultValue = "1") int lowest, @RequestParam(name="highest",defaultValue = "1000")int highest) {
        List<ViewType> ourView = viewService.readAllViewTypes(type,lowest,highest);
        return ResponseEntity.ok(ourView);
    }
    @GetMapping("/content")
    public ResponseEntity<List<ViewComments>> getCommentsForEstablishmentsByName(@RequestParam String name) {
        List<ViewComments> ourView = viewService.readAllViewCommentsByEstablishmentName(name);
        return ResponseEntity.ok(ourView);
    }


    @GetMapping("/pieces")
    public String getAllCounts() {
        List<ViewCount> counts = viewService.getAllCounts();
        return "Welcome in our Trippy Application.\nWe offer you to look from "+counts.get(0).getCountTown()+" towns, "
                +counts.get(0).getCountType()+" categories, "+counts.get(0).getCountCompany()+" companies, "+ counts.get(0).getCountUsers()+" users and "+
                counts.get(0).getCountComment() +" comments !";
    }




}
