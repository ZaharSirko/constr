package com.example.constr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.constr.model.TourRatings;
@Repository
public interface TourRatingsRepository extends JpaRepository<TourRatings,Long>  {
    
}
