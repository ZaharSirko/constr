package com.example.constr.service;



import org.springframework.stereotype.Service;

import com.example.constr.model.CreditCard;
import com.example.constr.model.User;
import com.example.constr.repo.CreditCardRepository;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public CreditCard getCreditCardByUserId(int userId) {
        return creditCardRepository.findByUserId(userId);
    }

    public CreditCard getCreditCardsByUserName(String userName) {
        return creditCardRepository.findByUserName(userName);
    }
    public CreditCard addCreditCardToUser(CreditCard creditCard, int userId) {
        creditCard.setUser(new User(userId)); 
        return creditCardRepository.save(creditCard);
    }

    public CreditCard updateCreditCard(CreditCard updatedCreditCard) {
        return creditCardRepository.save(updatedCreditCard);
    }

    public void deleteCreditCard(Long cardId) {
        creditCardRepository.deleteById(cardId);
    }

    public CreditCard addCreditCardToUser(CreditCard creditCard, String userName) {
        creditCard.setUser(new User(userName)); 
        return creditCardRepository.save(creditCard);
    }
}
