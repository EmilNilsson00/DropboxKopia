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
import org.springframework.stereotype.Service;
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

    public String uploadFile(MultipartFile file, UUID folderId) throws IOException,
            UnauthorizedException,
            NotfoundException {
        User user = CheckAuth.checkAuth();

        Folder folder = folderRepository.findFolderById(folderId)
                .orElseThrow(() -> new NotfoundException("File with ID: " + folderId + " not found"));

        if (!folder.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Unauthorized to Upload to folder with ID: " + folderId);
        }
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
