package com.example.dropbox.controllers;

import com.example.dropbox.dtos.RegisterDto;
import com.example.dropbox.security.AuthenticationResponse;
import com.example.dropbox.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterDto request) {
        AuthenticationResponse response = userService.register(request);
        if (response.isError()){
            return ResponseEntity.badRequest().body((response));
        }
        return ResponseEntity.ok(response);
    }

}
