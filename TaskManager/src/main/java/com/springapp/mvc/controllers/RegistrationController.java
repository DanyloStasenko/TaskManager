package com.springapp.mvc.controllers;

import com.springapp.mvc.models.User;
import com.springapp.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class RegistrationController {

    @Autowired(required = true)
    @Qualifier(value = "userService")
    private UserService userService;

    /**
     * Creating page for registration
     */
    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Registering new user (available for unauthorized users)
     */
    @RequestMapping(value = "/register/adduser", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        userService.addUser(user);
        return "redirect:/tasks";
    }
}
