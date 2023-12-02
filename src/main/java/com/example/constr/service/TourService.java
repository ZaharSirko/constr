package com.example.constr.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import com.example.constr.model.Tour;
import com.example.constr.repo.TourRepository;

import java.util.List;

@Service
public class TourService {

    private final TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public List<Tour> getToursByTourName(String tourName) {
        return tourRepository.findByTourName(tourName);
    }

    // інші методи
}


    // public List<Tour> getAllTours() {
    //     return tourRepository.findAll();
    // }

    // public Tour getTourById(int id) {
    //     return tourRepository.findById(id).orElse(null);
    // }

    // public Tour createTour(Tour tour) {
    //     return tourRepository.save(tour);
    // }

    // public Tour updateTour(int id, Tour tour) {
    //     if (tourRepository.existsById(id)) {
    //         tour.setId(id);
    //         return tourRepository.save(tour);
    //     }
    //     return null;
    // }

    // public void deleteTour(int id) {
    //     tourRepository.deleteById(id);
    // }