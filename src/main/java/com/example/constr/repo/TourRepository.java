package com.example.constr.repo;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.constr.model.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour,Long> {
    @Query("SELECT t FROM Tour t WHERE t.tourName = :tourName")
    Tour findByTourName(@Param("tourName") String tourName);
    Optional<Tour> findById(int id);
      void deleteById(int id);
}

