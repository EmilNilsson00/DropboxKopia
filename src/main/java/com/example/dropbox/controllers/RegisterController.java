package com.example.dropbox.controllers;

import com.example.dropbox.dtos.RegisterDto;
import com.example.dropbox.dtos.AuthenticationResponseDto;
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
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterDto request) {
        AuthenticationResponseDto response = userService.register(request);
        if (response.isError()){
            return ResponseEntity.badRequest().body((response));
        }
        return ResponseEntity.ok(response);
    }

}
