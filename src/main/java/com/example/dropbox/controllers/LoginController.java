package com.example.dropbox.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@RestController
public class LoginController {

    private AuthenticationProvider authProvider;

    @Autowired
    public LoginController(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @PostMapping("/login")
    public String login(@RequestHeader String username, @RequestHeader String password) {
        var auth = new UsernamePasswordAuthenticationToken(username, password);
        var result = authProvider.authenticate(auth);

        if (result.isAuthenticated()) {
            var algoritm = Algorithm.HMAC256("Hej William");
            var token = JWT.create()
                    .withSubject(username)
                    .withIssuer("auth0")
                    .sign(algoritm);

            return token;
        }

        return "Failed to login.";
    }
}

