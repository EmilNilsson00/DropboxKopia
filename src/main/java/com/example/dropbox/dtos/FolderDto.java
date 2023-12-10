package com.example.dropbox.dtos;

import com.example.dropbox.models.Folder;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FolderDto {
    private boolean error;
    private String message;
    private Folder folder;
    private List<Folder> folderList;
}
