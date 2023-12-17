package com.example.constr.controllers;


import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


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

    // @PutMapping("/profile/{userName}/credit-cards/{cardId}")
    // public ResponseEntity<CreditCard> updateCreditCard(
    //         @RequestBody CreditCard updatedCreditCard,
    //         @PathVariable int cardId,
    //         @PathVariable String userName,
    //         Model model
    // ) {
    //     updatedCreditCard.setId(cardId);
    //     CreditCard updatedCard = creditCardService.updateCreditCard(updatedCreditCard);
    //     return ResponseEntity.ok(updatedCard);
    // }

    // @DeleteMapping("/profile/{userName}/credit-cards/{cardId}")
    // public ResponseEntity<Void> deleteCreditCard(@PathVariable Long cardId, @PathVariable String userName) {
    //     creditCardService.deleteCreditCard(cardId);
    //     return ResponseEntity.noContent().build();
    // }
}