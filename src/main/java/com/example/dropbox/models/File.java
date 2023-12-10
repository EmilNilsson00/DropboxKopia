package com.example.dropbox.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String type;
    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

    @ManyToOne
    private Folder folder;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
}