package com.example.constr.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;

import com.example.constr.model.Tour;
import com.example.constr.model.TourImages;
import com.example.constr.repo.TourImagesRepository;
import com.example.constr.service.TourImagesService;
import com.example.constr.service.TourService;

@Controller
public class MainController {
    private final TourService tourService;
    private final TourImagesService tourImagesService;

    @Autowired
    public MainController(TourService tourService,TourImagesService tourImagesService) {
        this.tourService = tourService;
        this.tourImagesService = tourImagesService;
    }

	@GetMapping("/")
	public String home( Model model) { 
	List<Tour> tours = tourService.getAllTours(); 
    Map<Integer, List<TourImages>> tourImagesMap = new HashMap<>();


    for (Tour tour : tours) {
        List<TourImages> tourImages = tourImagesService.getTourImagesByTourName(tour);
        tourImagesMap.put(tour.getId(), tourImages);
    }
	model.addAttribute("tour", tours);
    model.addAttribute("tourImages", tourImagesMap);
	model.addAttribute("title", "Main Page");
		return "home";
	}

}