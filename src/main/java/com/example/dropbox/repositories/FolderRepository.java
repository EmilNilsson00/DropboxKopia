package com.example.dropbox.repositories;

import com.example.dropbox.models.Folder;
import com.example.dropbox.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {

    Optional<Folder> findFolderById(UUID id);

}
