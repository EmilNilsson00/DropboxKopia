package com.example.dropbox.services;

import Exceptions.NotfoundException;
import Exceptions.UnauthorizedException;
import com.example.dropbox.models.Folder;
import com.example.dropbox.models.User;
import com.example.dropbox.repositories.FolderRepository;
import com.example.dropbox.security.CheckAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    public
    User user = CheckAuth.checkAuth();

    public Folder createFolder(Folder folderRequest) throws IllegalArgumentException {
        User user = CheckAuth.checkAuth();

        if (folderRequest == null || folderRequest.getName() == null || folderRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid folder name.");
        }

        Folder folder = Folder.builder()
                .name(folderRequest.getName())
                .user(user)
                .build();
        folderRepository.save(folder);

        return folder;
    }

    public Folder deleteFolderById(UUID id) throws UnauthorizedException, NotfoundException {
        User user = CheckAuth.checkAuth();
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("Folder with id '" + id + "' not found."));

        if (!folder.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You aren't authorized to complete this action.");
        }

        folderRepository.deleteById(id);
        return folder;
    }
}
