package com.example.dropbox.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;

    @ManyToOne
    private Folder folder;
}