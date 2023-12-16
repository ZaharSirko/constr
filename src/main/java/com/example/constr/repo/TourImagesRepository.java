package com.example.constr.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.constr.model.Tour;
import com.example.constr.model.TourImages;

@Repository
public interface TourImagesRepository  extends JpaRepository<TourImages,Long> {
    @Query("SELECT t_i FROM TourImages t_i WHERE t_i.tour.id = :TourId")
    List<TourImages> findByTourId(@Param("TourId")int TourId);
    List<TourImages> findByTour(Tour tour);
}
