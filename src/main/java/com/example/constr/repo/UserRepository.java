package com.example.constr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.constr.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    User findByUsername(@Param("userName") String username);
    @Query(value = "SELECT nextval(pg_get_serial_sequence('user', 'id'))", nativeQuery = true)
    Long getNextId();
}
