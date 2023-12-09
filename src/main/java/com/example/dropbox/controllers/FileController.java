package com.example.dropbox.controllers;

import com.example.dropbox.services.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class FileController {

    private FileService service;

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        String uploadFile = service.uploadFile(file);
        return ResponseEntity.ok(uploadFile);
    }

    @GetMapping("/downloadFile/{fileName}")
    public  ResponseEntity<?> downloadFile(@PathVariable String fileName) throws FileNotFoundException {
        byte[] file = service.downloadFile(fileName);
        return ResponseEntity.ok(file);

    }

}
