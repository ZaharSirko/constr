package com.example.constr.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.constr.model.CreditCard;
import com.example.constr.model.Tour;
import com.example.constr.model.TourImages;
import com.example.constr.model.User;
import com.example.constr.service.CreditCardService;
import com.example.constr.service.TourImagesService;
import com.example.constr.service.TourService;
import com.example.constr.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
public class TourController {

    private final TourService tourService;
    private final UserService userService;
    private final CreditCardService creditCardService;
    private final TourImagesService tourImagesService;
    @Autowired
    public TourController(TourService tourService,UserService userService,CreditCardService creditCardService,TourImagesService tourImagesService) {
        this.tourService = tourService;
        this.userService = userService;
        this.creditCardService = creditCardService;
        this.tourImagesService = tourImagesService; 
        
    }
    
    @GetMapping(value = "/tour/{tourName}")
    public String getToursByTourNameDetails(@PathVariable String tourName, Model model, Principal principal) {
      
        if(principal != null){
            String userName = principal.getName();
            User user = userService.getUserByUserName(userName);
            CreditCard creditCard = creditCardService.getCreditCardsByUserName(userName);
            boolean hasCreditCard = (user != null && creditCard != null);
            model.addAttribute("hasCreditCard", hasCreditCard);
            model.addAttribute("creditCard", creditCard);
        }
        Tour tour = tourService.getToursByTourName(tourName);
        List<TourImages> tourImages = tourService.getImagesForTour(tour.getId());
        boolean limit = tour.getCurrentNumberOfPeople() < tour.getMaxNumberOfPeople();
      
        model.addAttribute("limit", limit);
        model.addAttribute("tour", tour);
        model.addAttribute("tourImages", tourImages);
        return "tour-detail"; 
    }

    @PostMapping(value = "/tour/{tourName}")
    public String addTourToUserBasket(@PathVariable String tourName, Principal principal) {
        Tour tour = tourService.getToursByTourName(tourName);
        String userName = principal.getName();
        User user = userService.getUserByUserName(userName);
        float tourPrice = tour.getPrice();
        if (user != null && user.getCurrency() >= tourPrice) {
            boolean isTourAlreadyAdded = user.getTours().contains(tour);
            if(!isTourAlreadyAdded){
            userService.topUpBalance(user, -tourPrice);
            userService.addTourToUserBasket(userName, tour.getId());
            }
        }
        else{
            return "redirect:/profile/"+userName+"/credit-cards/top-up";
        }
        return "redirect:/tour/basket/"+userName;
    }

    @GetMapping(value = "/tour/basket/{userName}")
    public String getTourBasketOfUser(@PathVariable String userName, Model model) {
        Set<Tour> userTours = tourService.getToursByUserName(userName);
         Map<Integer, List<TourImages>> tourImagesMap = new HashMap<>();
        for (Tour tour : userTours) {
            List<TourImages> tourImages = tourImagesService.getTourImagesByTourName(tour);
            tourImagesMap.put(tour.getId(), tourImages);
        }
        model.addAttribute("tourImages", tourImagesMap);
        model.addAttribute("userTours", userTours);
        return "tour-basket";
    }

    @PostMapping(value = "/tour/basket/{userName}/cancel/{tourId}")
    public String cancelTourReservation( @PathVariable String userName,@PathVariable int tourId  ) {
        User user = userService.getUserByUserName(userName);
        Tour tour = tourService.getTourById(tourId);
        if (user != null && tour != null && user.getTours().contains(tour)) {
            float tourPrice = tour.getPrice(); 
            float insurance_payment = (5f / 100f) * tourPrice; 
            float discountedPrice = tourPrice - insurance_payment;
            userService.topUpBalance(user, discountedPrice);
            userService.removeTourFromUserBasket(userName, tourId);
        } 
    
        return "redirect:/tour/basket/" + userName;
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

    @GetMapping("/tour/edit/{tourId}")
    public String showEditTourForm(@PathVariable("tourId") int tourId, Model model) {
        Tour tour = tourService.getTourById(tourId);
        if (tour != null) {
            model.addAttribute("tour", tour);
            return "tour-edit";
        } else {
            return "redirect:/"; 
        }
    }

    @PostMapping("/tour/edit/{tourId}")
    public String editTour(@PathVariable("tourId") int tourId, @ModelAttribute Tour updatedTour, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/"; 
        } else {
                tourService.updateTour(tourId, updatedTour);
                return "redirect:/";
        }
    }

    @PostMapping("/tour/delete/{id}")
    public String deleteTour(@PathVariable("id") int id) {
        tourService.deleteTourById(id); 
        return "redirect:/"; 
    }
}

