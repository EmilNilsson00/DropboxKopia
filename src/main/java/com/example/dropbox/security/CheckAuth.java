package com.example.dropbox.security;


import Exceptions.InvalidTokenException;
import com.example.dropbox.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CheckAuth {

    public static User checkAuth() throws InvalidTokenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (User) authentication.getPrincipal();
        }
        throw new InvalidTokenException("User not found, please include valid token in request header");
    }
}