package com.example.constr.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;


import com.example.constr.model.Tour;
import com.example.constr.model.TourImages;
import com.example.constr.repo.TourImagesRepository;
import com.example.constr.repo.TourRepository;

import java.io.IOException;
import java.util.List;


@Service
public class TourService {

    private final TourRepository tourRepository;
    private final TourImagesService tourImageService;
    private final TourImagesRepository tourImagesRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourImagesService tourImageService, TourImagesRepository tourImagesRepository) {
        this.tourRepository = tourRepository;
        this.tourImageService = tourImageService;
        this.tourImagesRepository = tourImagesRepository;
    }

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Tour getTourById(int id) {
        return tourRepository.findById(id).orElse(null);
    }

       public Tour getToursByTourName(String tourName) {
        return tourRepository.findByTourName(tourName);
    }

    public Tour createTour(Tour tour, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = tourImageService.saveImage(imageFile);

            TourImages tourImages = new TourImages();
            tourImages.setImageUrl(imageUrl);
            tourImages.setTour(tour);

            tourImagesRepository.save(tourImages);
        }

        return tourRepository.save(tour);
    }

    // public Tour getTourById(int id) {
    //     return tourRepository.findById(id).orElse(null);
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

   // public List<Tour> getToursByTourName(String tourName) {
    //     return tourRepository.findByTourName(tourName);
    // }

    // інші методи
}



  