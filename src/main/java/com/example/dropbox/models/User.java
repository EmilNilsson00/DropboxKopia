package com.example.dropbox.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String role;

    @OneToMany
    private List<Folder> folders;

    public User( String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
