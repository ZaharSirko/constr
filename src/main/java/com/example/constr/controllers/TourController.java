package com.example.constr.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.example.constr.model.Tour;
import com.example.constr.model.TourImages;
import com.example.constr.service.TourService;
import com.example.constr.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@Controller
public class TourController {

    private final TourService tourService;
    private final UserService userService;

    @Autowired
    public TourController(TourService tourService,UserService userService) {
        this.tourService = tourService;
        this.userService = userService;
    }
    
    @GetMapping(value = "/tour/{tourName}")
    public String getToursByTourNameDetails(@PathVariable String tourName, Model model) {
        Tour tour = tourService.getToursByTourName(tourName);
            List<TourImages> tourImages = tourService.getImagesForTour(tour.getId());
            model.addAttribute("tour", tour);
            model.addAttribute("tourImages", tourImages);
            return "tour-detail"; 
    }

    @PostMapping(value = "/tour/{tourName}")
    public String addTourToUserBasket(@PathVariable String userName, @PathVariable int tourId) {
        userService.addTourToUserBasket(userName, tourId);
        return "redirect:/";
    }

    @GetMapping(value = "/tour/basket/{userName}")
    public String getTourBasketOfUser(@PathVariable String userName, Model model) {
        Set<Tour> userTours = tourService.getToursByUserName(userName);

        model.addAttribute("userTours", userTours);

        return "tour-basket";
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

