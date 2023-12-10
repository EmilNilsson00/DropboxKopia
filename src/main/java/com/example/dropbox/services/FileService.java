package com.example.dropbox.services;

import Exceptions.NotfoundException;
import Exceptions.UnauthorizedException;
import com.example.dropbox.models.File;
import com.example.dropbox.models.Folder;
import com.example.dropbox.models.User;
import com.example.dropbox.repositories.FileRepository;
import com.example.dropbox.repositories.FolderRepository;
import com.example.dropbox.security.CheckAuth;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FolderRepository folderRepository;

    @Transactional
    public String uploadFile(MultipartFile file, UUID folderId) throws IOException,
            UnauthorizedException,
            NotfoundException {
        User user = CheckAuth.checkAuth();
        System.out.println("1");
        Folder folder = folderRepository.findFolderById(folderId)
                .orElseThrow(() -> new NotfoundException("File with ID: " + folderId + " not found"));
        System.out.println("2");
        if (!folder.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Unauthorized to Upload to folder with ID: " + folderId);
        }
        System.out.println("3");
        File response = fileRepository.save(File.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .folder(folder)
                .user(user)
                .fileData(file.getBytes()).build());
        if (response != null) {
            return "File uploaded: " + file.getOriginalFilename();
        }
        return null;
    }

    @Transactional
    public File downloadFile(String fileName) throws FileNotFoundException {
        User user = CheckAuth.checkAuth();

        Optional<File> dbFile = fileRepository.findByName(fileName);
        if (dbFile.isPresent()) {
            var file =  dbFile.get();
            if (!file.getUser().getId().equals(user.getId())) {
                throw new UnauthorizedException("Unauthorized to download file with ID: " + file.getId());
            }
            return file;
        } else {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }

    public String deleteFileById(UUID id) throws FileNotFoundException {
        User user = CheckAuth.checkAuth();
        System.out.println("1");
        File dbFile = fileRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("File not found"));
        System.out.println("2");
        if (!dbFile.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Unauthorized to delete file with ID: " + dbFile.getId());
        }
        System.out.println("3");
        fileRepository.deleteById(id);
        System.out.println("4");
        return "Successfully deleted file with id: " + id;
    }



}
