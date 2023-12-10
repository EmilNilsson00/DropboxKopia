package com.example.dropbox.controllers;

import com.example.dropbox.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/files")
@RestController
public class FileController {

    @Autowired
    private FileService service;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("folderId") UUID folderId) throws IOException {

        String uploadFile = service.uploadFile(file, folderId);
        return ResponseEntity.ok(uploadFile);
    }

    @GetMapping("/download/{fileName}")
    public  ResponseEntity<?> downloadFile(@PathVariable String fileName) throws FileNotFoundException {
        byte[] file = service.downloadFile(fileName);
        return ResponseEntity.ok(file);

    }

}
