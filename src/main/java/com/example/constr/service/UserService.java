package com.example.constr.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.constr.model.Role;
import com.example.constr.model.Tour;
import com.example.constr.model.User;
import com.example.constr.repo.RoleRepository;
import com.example.constr.repo.TourRepository;
import com.example.constr.repo.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserService implements UserDetailsService  {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TourRepository tourRepository;
    @Bean
   public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
}


    public boolean saveUser(User user) {
         User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(encoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void addTourToUserBasket(String userName, int tourId) {
        User user = userRepository.findByUsername(userName);
        Tour tour = tourRepository.findById(tourId).orElse(null);

        if (user != null && tour != null && tour.getCurrentNumberOfPeople() < tour.getMaxNumberOfPeople()) {
            boolean isTourAlreadyAdded = user.getTours().contains(tour);
            if (!isTourAlreadyAdded) {
                tour.setCurrentNumberOfPeople(tour.getCurrentNumberOfPeople() + 1);
                user.getTours().add(tour);
                userRepository.save(user);
            }
        }
    }
    public void removeTourFromUserBasket(String userName, int tourId) {
        User user = userRepository.findByUsername(userName);
        Tour tour = tourRepository.findById(tourId).orElse(null);
        
    
        if (user != null && tour != null) {
            tour.setCurrentNumberOfPeople(tour.getCurrentNumberOfPeople()-1);
            user.getTours().remove(tour); 
            userRepository.save(user);
        }
    }
    
    public void topUpBalance(User user, float amount) {
        float currentBalance = user.getCurrency();
        float newBalance = currentBalance + amount;
        user.setCurrency(newBalance);
        userRepository.save(user);
    }

    public boolean updateUser(String userName, User updatedUser) {
        User existingUser = userRepository.findByUsername(userName);
        if (existingUser != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setSecondName(updatedUser.getSecondName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            userRepository.save(existingUser); 
            return true;
        }
        return false;
    }
    
    }
    
