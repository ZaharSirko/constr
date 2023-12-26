package com.example.constr.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.constr.model.User;
import com.example.constr.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;



@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-in")
    public String registration(Model model) {
        model.addAttribute("user", new User());

        return "sign-in";
    }


    @PostMapping("/sign-in")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "sign-in";
        }
        if (!userService.saveUser(user)){
            model.addAttribute("usernameError", "User with this login has been created");
            return "sign-in";
        }

        return "redirect:/";
    }


    @GetMapping("/log-in")
    public String log_in(Model model) {

        return "log-in";
    }
    
    @PostMapping("/log-in")
    public String loginUser(@RequestParam("login") String login,
                            @RequestParam("password") String password,
                            Model model) {
        try {

            return "redirect:/"; 
        } catch (Exception e) {
            model.addAttribute("loginError", e.getMessage()); 
            return "log-in"; 
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request,response, auth);
        }
        return "redirect:/";
    }

    @GetMapping(value = "/profile/{userName}")
    public String getUserProfile(@PathVariable String userName, Model model) {
        User user = userService.getUserByUserName(userName);
        if (user != null) {
            model.addAttribute("user", user);
        } else {
        }
            return "user-profile"; 
    }

    @GetMapping(value = "/profile/{userName}/edit")
    public String getUserProfileEdit(@PathVariable String userName, Model model) {
        User user = userService.getUserByUserName(userName);
        if (user != null) {
            model.addAttribute("user", user);
        } else {
        }
            return "user-profile-edit"; 
    }
    @PostMapping("/profile/{userName}/edit")
    public String editUser(@PathVariable("userName") String userName, @ModelAttribute("updatedUser") User updatedUser) {
        boolean isUpdated = userService.updateUser(userName, updatedUser);

        if (isUpdated) {
            return "redirect:/profile/" + userName; 
        } else {
 
            return "redirect:/error"; 
        }
    }

}

    
