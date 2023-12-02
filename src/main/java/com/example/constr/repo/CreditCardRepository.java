package com.example.constr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.constr.model.CreditCard;
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
    
}
