package com.chakans.portal.repository;

import com.chakans.portal.domain.FileUploadAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the FileUploadAlbum entity.
 */
public interface FileUploadAlbumRepository extends JpaRepository<FileUploadAlbum, String> {

    List<FileUploadAlbum> findAllByCreatedDateBefore(Instant createdDate);
}
