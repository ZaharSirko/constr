package com.example.constr.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.constr.model.Tour;
import com.example.constr.model.TourImages;
import com.example.constr.repo.TourImagesRepository;
import com.example.constr.service.TourImagesService;
import com.example.constr.service.TourService;

import java.io.IOException;
import java.util.List;


@Controller
public class TourController {

    private final TourService tourService;
    private final TourImagesService tourImagesService;

    @Autowired
    public TourController(TourService tourService,TourImagesService tourImagesService) {
        this.tourService = tourService;
        this.tourImagesService = tourImagesService;
    }
    
    // getToursByTourName
    // getTourImagesByTourId
    @GetMapping("/tour/{tourName}")
    public String getToursByTourName(@PathVariable String tourName, Model model) {
        Tour tour = tourService.getToursByTourName(tourName);
        if (tour != null) {
            List<TourImages> tourImages = tourImagesService.getTourImagesByTourName(tour);
            model.addAttribute("tour", tour);
            model.addAttribute("tourImages", tourImages);
            return "tour"; 
        } else {
            return "tourNotFound"; 
        }
    }
    
    @GetMapping("/tour/add")
    public String addTour(Model model) {
        return "tour-add"; 
    }

    @PostMapping("/tour/add")
    public String addNewTour(@ModelAttribute Tour newTour,BindingResult bindingResult, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (bindingResult.hasErrors()) {
            return "redirect:/about";
        }
      else{
        tourService.createTour(newTour, imageFile);
        return "redirect:/";
      }
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

//     @GetMapping("/tour/{tourId}")
// public String getTourDetails(@PathVariable Long tourId, Model model) {
//     Tour tour = tourService.getTourById(tourId); // Отримати тур за ідентифікатором
//     List<TourImages> tourImages = tourImagesRepository.findByTourId(tourId); // Отримати зображення для цього туру

//     model.addAttribute("tour", tour);
//     model.addAttribute("tourImages", tourImages);
//     return "tourDetails"; // Переходимо на сторінку з деталями туру
// }
