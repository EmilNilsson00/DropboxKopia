package com.example.dropbox.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GetFolderDto {
    private UUID folderId;
    private String folderName;
    private List<GetFileDto> files;
}
