package com.example.constr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.constr.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}