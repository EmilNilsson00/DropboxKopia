package com.example.dropbox.controllers;

import Exceptions.UnauthorizedException;
import com.example.dropbox.dtos.FolderDto;
import com.example.dropbox.dtos.GetFolderDto;
import com.example.dropbox.models.Folder;
import com.example.dropbox.services.FolderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/folder")
@RestController
public class FolderController {

    @Autowired
    FolderService folderService;


    @PostMapping("/create")
    public ResponseEntity<Folder> createFolder(@RequestBody FolderDto folderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(folderService.createFolder(folderDto.getFolder()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFolder(@PathVariable UUID id) {
        return ResponseEntity.ok(folderService.deleteFolderById(id));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<GetFolderDto> getFolderById(@PathVariable UUID id) throws UnauthorizedException {
        return ResponseEntity.ok(folderService.getFolderById(id));
    }

}
