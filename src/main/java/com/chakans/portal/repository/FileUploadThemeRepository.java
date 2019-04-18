package com.chakans.portal.repository;

import com.chakans.portal.domain.FileUploadTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the FileUploadTheme entity.
 */
public interface FileUploadThemeRepository extends JpaRepository<FileUploadTheme, String> {

    List<FileUploadTheme> findAllByCreatedDateBefore(Instant createdDate);
}
