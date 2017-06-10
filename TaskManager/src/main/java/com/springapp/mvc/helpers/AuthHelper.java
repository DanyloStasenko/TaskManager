package com.springapp.mvc.helpers;

import com.springapp.mvc.models.User;
import com.springapp.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    @Autowired(required = true)
    @Qualifier(value = "userService")
    private UserService userService;

    public User getLoggedInUser(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByName(loggedInUser.getName());
    }
}
