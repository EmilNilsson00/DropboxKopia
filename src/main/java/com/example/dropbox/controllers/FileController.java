package com.example.dropbox.controllers;

import Exceptions.UnauthorizedException;
import com.example.dropbox.models.File;
import com.example.dropbox.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("folderId") UUID folderId) throws IOException {

        String uploadFile = service.uploadFile(file, folderId);
        return ResponseEntity.ok(uploadFile);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable UUID id) throws FileNotFoundException {
        File fileContent = service.downloadFile(id);
        ByteArrayResource resource = new ByteArrayResource(fileContent.getFileData());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        headers.setContentDispositionFormData("attachment", fileContent.getName());

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable UUID id) {
        return ResponseEntity.ok(service.deleteFileById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<File> getFileById(@PathVariable UUID id) throws UnauthorizedException {
        return ResponseEntity.ok(service.getFileById(id));
    }

}
