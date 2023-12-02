package com.example.constr.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;

import com.example.constr.model.User;
import com.example.constr.repo.UserRepository;

@Service
public class UserService  {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUserLogin(user.getLogin());

        if (userFromDB != null) {
            return false;
        }

        user.setRole("ROLE_USER");
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public User loginUser(String login, String password) throws Exception {
        User user = userRepository.findByUserLogin(login);
        
        if (user == null || !user.getPassword().equals(password)) {
            throw new Exception("Invalid login credentials");
        }
        
        return user;
    }

    // public boolean isLoggedIn() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     return authentication != null && authentication.isAuthenticated();
    // }

}
