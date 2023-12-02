package com.example.constr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.constr.model.TourImages;
@Repository
public interface TourImagesRepository  extends JpaRepository<TourImages,Long> {
    
}
