package com.example.dropbox.services;

import com.example.dropbox.models.File;
import com.example.dropbox.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public String uploadFile(MultipartFile file) throws IOException {
        File response = fileRepository.save(File.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .fileData(file.getBytes())
                .fileData(file.getBytes()).build());
        if (response != null) {
            return "File uploaded: " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadFile(String fileName) throws FileNotFoundException {
        Optional<File> dbFile = fileRepository.findFileByName(fileName);
        if (dbFile.isPresent()) {
            return dbFile.get().getFileData();
        } else {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }
}
