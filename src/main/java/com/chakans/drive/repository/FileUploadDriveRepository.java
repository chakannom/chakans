package com.chakans.drive.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.drive.domain.FileUploadDrive;

/**
 * Spring Data JPA repository for the {@link FileUploadDrive} entity.
 */
public interface FileUploadDriveRepository extends JpaRepository<FileUploadDrive, String> {

    List<FileUploadDrive> findAllByCreatedDateBefore(Instant createdDate);
}
