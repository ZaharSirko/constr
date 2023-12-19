package com.example.constr.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;


import com.example.constr.model.Tour;
import com.example.constr.model.TourImages;
import com.example.constr.model.User;
import com.example.constr.repo.TourImagesRepository;
import com.example.constr.repo.TourRepository;
import com.example.constr.repo.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@Service
public class TourService {

    private final TourRepository tourRepository;
    private final TourImagesService tourImageService;
    private final TourImagesRepository tourImagesRepository;
    private final UserRepository  userRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourImagesService tourImageService, TourImagesRepository tourImagesRepository, UserRepository  userRepository) {
        this.tourRepository = tourRepository;
        this.tourImageService = tourImageService;
        this.tourImagesRepository = tourImagesRepository;
        this.userRepository = userRepository;
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

    public List<TourImages> getImagesForTour(int tourId) {
        return tourImagesRepository.findByTourId(tourId);
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

    public Set<Tour> getToursByUserName(String userName) {
        User user = userRepository.findByUsername(userName);
        return user.getTours();
    }
    
    public boolean saveTour(Tour tour) {
        Tour tourFromDB = tourRepository.findByTourName(tour.getTourName());
       if (tourFromDB != null) {
           return false;
       }
       tourRepository.save(tour);
       return true;
   }
}



  