package com.example.dropbox.controllers;

import com.example.dropbox.dtos.FolderDto;
import com.example.dropbox.models.Folder;
import com.example.dropbox.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/folder")
@RestController
public class FolderController {

    @Autowired
    FolderService folderService;


    @PostMapping("/create")
    public ResponseEntity<Folder> createFolder (@RequestBody FolderDto folderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(folderService.createFolder(folderDto.getFolder()));
    }
}
