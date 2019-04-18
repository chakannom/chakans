package com.chakans.drive.service;

import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chakans.core.config.constants.Constants;
import com.chakans.core.security.SecurityUtils;
import com.chakans.drive.domain.FileUploadDrive;
import com.chakans.drive.repository.FileUploadDriveRepository;
import com.chakans.portal.config.ApplicationProperties;
import com.chakans.portal.repository.MinioRepository;

/**
 * Service class for managing drive_storage.
 */
@Service
@Transactional
public class DriveStorageService {

    private final Logger log = LoggerFactory.getLogger(DriveStorageService.class);

    private final MinioRepository minioRepository;

    private final FileUploadDriveRepository fileUploadDriveRepository;

    private final ApplicationProperties applicationProperties;

    public DriveStorageService(MinioRepository minioRepository, FileUploadDriveRepository fileUploadDriveRepository, ApplicationProperties applicationProperties) {
        this.minioRepository = minioRepository;
        this.fileUploadDriveRepository = fileUploadDriveRepository;
        this.applicationProperties = applicationProperties;
    }

    public Optional<String> getPresignedPutUrl(String filename) {
        String driveBucket = applicationProperties.getMinio().getBuckets().get(Constants.BUCKETS_DRIVE);
        String prefixFolderPath = generatePrefixFolderPath();
        String fileFolder = getDriveFileFolder(driveBucket, prefixFolderPath);
        String objectName = Paths.get(prefixFolderPath, fileFolder, filename).toString().replaceAll("\\\\", "/");
        return Optional.of(minioRepository.getPresignedPutUrl(driveBucket, objectName));
    }

    private String generatePrefixFolderPath() {
        String userLogin = SecurityUtils.getCurrentUserLogin().get();
        return Paths.get(userLogin).toString().replaceAll("\\\\", "/");
    }

    private String getDriveFileFolder(String bucketName, String parentPath) {
        for (int i = 0; i < 5; i++) {
            String fileFolder = minioRepository.getFileFolder(bucketName, parentPath);
            String directory = Paths.get(parentPath, fileFolder).toString().replaceAll("\\\\", "/");
            if (!fileUploadDriveRepository.findById(directory).isPresent()) {
                FileUploadDrive fileUploadDrive = new FileUploadDrive();
                fileUploadDrive.setDirectory(directory);
                fileUploadDriveRepository.save(fileUploadDrive);
                return fileFolder;
            }
        }
        return null;
    }
}
