package com.example.dropbox.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GetFileDto {
    private UUID fileId;
    private String fileName;
    private String fileDownloadLink;
}
