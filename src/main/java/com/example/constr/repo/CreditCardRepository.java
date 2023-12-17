package com.example.constr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.constr.model.CreditCard;
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
    List<CreditCard> findByUserId(int userId);
    @Query("SELECT u_c FROM CreditCard u_c WHERE u_c.user.userName = :userName")
    CreditCard findByUserName(String userName);
}
