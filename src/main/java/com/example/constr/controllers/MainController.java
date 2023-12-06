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
import com.example.constr.service.TourService;

@Controller
public class MainController {
    private final TourService tourService;
    private final TourImagesRepository tourImagesRepository;

    @Autowired
    public MainController(TourService tourService,TourImagesRepository tourImagesRepository) {
        this.tourService = tourService;
        this.tourImagesRepository = tourImagesRepository;
    }

	@GetMapping("/")
	public String home( Model model) { 
	List<Tour> tours = tourService.getAllTours(); 
    Map<Integer, List<TourImages>> tourImagesMap = new HashMap<>();


    for (Tour tour : tours) {
        List<TourImages> tourImages = tourImagesRepository.findByTour(tour);
        tourImagesMap.put(tour.getId(), tourImages);
    }
	model.addAttribute("tour", tours);
    model.addAttribute("tourImages", tourImagesMap);
	model.addAttribute("title", "Main Page");
		return "home";
	}

}