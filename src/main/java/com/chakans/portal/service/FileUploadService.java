package com.chakans.portal.service;

import com.chakans.portal.domain.FileUploadAlbum;
import com.chakans.portal.domain.FileUploadTheme;
import com.chakans.portal.repository.FileUploadAlbumRepository;
import com.chakans.portal.repository.FileUploadThemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service class for managing file_upload.
 */
@Service
@Transactional
public class FileUploadService {

    private final Logger log = LoggerFactory.getLogger(FileUploadService.class);

    private final FileUploadAlbumRepository fileUploadAlbumRepository;

    private final FileUploadThemeRepository fileUploadThemeRepository;

    public FileUploadService(FileUploadAlbumRepository fileUploadAlbumRepository, FileUploadThemeRepository fileUploadThemeRepository) {
        this.fileUploadAlbumRepository = fileUploadAlbumRepository;
        this.fileUploadThemeRepository = fileUploadThemeRepository;
    }

    /**
     * FileUploadAlbums should be automatically deleted after 1 day.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeFileUploadAlbums() {
        List<FileUploadAlbum> fileUploadAlbums =
            fileUploadAlbumRepository.findAllByCreatedDateBefore(Instant.now().minus(1, ChronoUnit.DAYS));
        for (FileUploadAlbum fileUploadAlbum : fileUploadAlbums) {
            log.debug("Deleting fileUploadAlbum {}", fileUploadAlbum.getDirectory());
            fileUploadAlbumRepository.delete(fileUploadAlbum);
        }
    }

    /**
     * FileUploadThemes should be automatically deleted after 1 day.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeFileUploadThemes() {
        List<FileUploadTheme> fileUploadThemes =
            fileUploadThemeRepository.findAllByCreatedDateBefore(Instant.now().minus(1, ChronoUnit.DAYS));
        for (FileUploadTheme fileUploadTheme : fileUploadThemes) {
            log.debug("Deleting fileUploadAlbum {}", fileUploadTheme.getDirectory());
            fileUploadThemeRepository.delete(fileUploadTheme);
        }
    }
}