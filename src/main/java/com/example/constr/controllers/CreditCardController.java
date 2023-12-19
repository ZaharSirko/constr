package com.example.constr.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.constr.model.CreditCard;
import com.example.constr.model.User;
import com.example.constr.service.CreditCardService;
import com.example.constr.service.UserService;


@Controller
public class CreditCardController {
    private final CreditCardService creditCardService;
    private final UserService userService;

    public CreditCardController(CreditCardService creditCardService,UserService userService) {
        this.creditCardService = creditCardService;
        this.userService = userService;
    }
    
    @GetMapping(value = "/profile/{userName}/credit-cards")
    public String getCreditCardsForUser(@PathVariable String userName,Model model ) {
        CreditCard creditCard = creditCardService.getCreditCardsByUserName(userName);
        if (creditCard==null) {
            return "redirect:/profile/{userName}/credit-cards/add";
        } else {    
            model.addAttribute("creditCard", creditCard);
            return "user-profile-credit-card";
        }
    }

    @GetMapping(value = "/profile/{userName}/credit-cards/add")
    public String getAddCreditCardsForUser( @PathVariable String userName) {
            return "user-profile-credit-card-add";
    }

    @PostMapping(value = "/profile/{userName}/credit-cards/add")
    public String addCreditCardToUser(@ModelAttribute("creditCard") CreditCard creditCard, @PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        if (user != null) {
            creditCard.setUser(user);
            creditCardService.addCreditCardToUser(creditCard, user.getId());
        }
        return "redirect:/profile/" + userName +"/credit-cards";
    }

    @GetMapping("/profile/{userName}/credit-cards/top-up")
    public String showTopUpForm(@PathVariable String userName, Model model) {
        User user = userService.getUserByUserName(userName);
        CreditCard creditCard = creditCardService.getCreditCardByUserId(user.getId());
        if (user != null) {
            if (creditCard == null) {
                return "redirect:/profile/{userName}/credit-cards/add";
            }
                model.addAttribute("creditCard",creditCard);
                model.addAttribute("user", user);
                return "user-top-up-balance"; 
        }
        return "redirect:/error"; 
    }

    @PostMapping(value = "/profile/{userName}/credit-cards/top-up")
    public String topUpUserBalance(@RequestParam("amount") float amount, @PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
                userService.topUpBalance(user, amount); 
                return "redirect:/profile/" + userName;
            
    }

}