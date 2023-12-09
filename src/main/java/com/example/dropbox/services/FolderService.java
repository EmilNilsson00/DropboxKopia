package com.example.dropbox.services;

import com.example.dropbox.models.Folder;
import com.example.dropbox.models.User;
import com.example.dropbox.repositories.FolderRepository;
import com.example.dropbox.security.CheckAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
