package com.example.dropbox.services;

import Exceptions.NotfoundException;
import Exceptions.UnauthorizedException;
import com.example.dropbox.dtos.GetFileDto;
import com.example.dropbox.dtos.GetFolderDto;
import com.example.dropbox.models.File;
import com.example.dropbox.models.Folder;
import com.example.dropbox.models.User;
import com.example.dropbox.repositories.FolderRepository;
import com.example.dropbox.security.CheckAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;


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

    public GetFolderDto getFolderById(UUID id) throws UnauthorizedException, NotfoundException {
        User user = CheckAuth.checkAuth();
        Folder dbFolder = folderRepository.findById(id)
                .orElseThrow(() -> new NotfoundException("Folder not found with id: " + id));

        List<GetFileDto> getFileDtos = dbFolder.getFiles()
                .stream()
                .map(file -> new GetFileDto(file.getId(), file.getName(), getFileDownloadLink(file)))
                .toList();

        String folderName = dbFolder.getName() != null ? dbFolder.getName() : "";
        System.out.println("Folder Name: " + folderName);

        GetFolderDto  getFolderDTO = new GetFolderDto(dbFolder.getId(), folderName, getFileDtos);

        if (!dbFolder.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Unauthorized to show folder with ID: " + dbFolder.getId());
        }

        return getFolderDTO;
    }
    private String getFileDownloadLink(File file) {

        return "/files/download/" + file.getId();
    }
}
