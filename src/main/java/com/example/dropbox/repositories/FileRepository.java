package com.example.dropbox.repositories;

import com.example.dropbox.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {
    Optional<File> findFileByName(String name);

    Optional<File> findByName(String name);

    Optional<File> findById(UUID id);

}
