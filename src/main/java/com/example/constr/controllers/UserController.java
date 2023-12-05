package com.example.constr.controllers;



import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    // private boolean isLoggedIn() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     return authentication != null  && authentication.isAuthenticated();
    // }

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

        return "redirect:/tour";
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

            return "redirect:/tour"; 
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
}

    
