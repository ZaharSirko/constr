package com.example.constr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.constr.model.Stats;
@Repository
public interface StatsRepository extends JpaRepository<Stats,Long> {
    
}
