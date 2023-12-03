package com.example.constr.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.constr.model.Tour;
import com.example.constr.service.TourService;

import java.util.List;

@Controller
@RequestMapping("/tour")
public class TourController {

    private final TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }
    
    @GetMapping 
    public String tourPage() {
        return "tour"; 
    }

    @GetMapping("/{tourName}")
    public List<Tour> getToursByTourName(@PathVariable String tourName) {
        return tourService.getToursByTourName(tourName);
    }


}


    // @GetMapping
    // public List<Tour> getAllTours() {
    //     return tourService.getAllTours();
    // }

    // @GetMapping("/{id}")
    // public Tour getTourById(@PathVariable Long id) {
    //     return tourService.getTourById(id);
    // }


    // @PostMapping
    // public Tour createTour(@RequestBody Tour tour) {
    //     return tourService.createTour(tour);
    // }

    // @PutMapping("/{id}")
    // public Tour updateTour(@PathVariable Long id, @RequestBody Tour tour) {
    //     return tourService.updateTour(id, tour);
    // }

    // @DeleteMapping("/{id}")
    // public void deleteTour(@PathVariable Long id) {
    //     tourService.deleteTour(id);
    // }