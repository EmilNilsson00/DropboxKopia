package com.example.dropbox.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    @GetMapping("/test")
    public String test(){
        return "hejsan svejsan";
    }
}
