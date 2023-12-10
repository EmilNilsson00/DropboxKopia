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

    List<Folder> findByUser(User user);
    Optional<Folder> findFolderById(UUID id);

}
