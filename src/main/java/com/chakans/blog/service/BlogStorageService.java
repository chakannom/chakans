package com.chakans.blog.service;

import com.chakans.core.config.constants.Constants;
import com.chakans.core.security.SecurityUtils;
import com.chakans.portal.config.ApplicationProperties;
import com.chakans.portal.domain.FileUploadAlbum;
import com.chakans.portal.repository.FileUploadAlbumRepository;
import com.chakans.portal.repository.MinioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing storage.
 */
@Service
@Transactional
public class BlogStorageService {

	private final Logger log = LoggerFactory.getLogger(BlogStorageService.class);

    private final MinioRepository minioRepository;

    private final FileUploadAlbumRepository fileUploadAlbumRepository;

    private final ApplicationProperties applicationProperties;

    public BlogStorageService(MinioRepository minioRepository, FileUploadAlbumRepository fileUploadAlbumRepository, ApplicationProperties applicationProperties) {
    	this.minioRepository = minioRepository;
    	this.fileUploadAlbumRepository = fileUploadAlbumRepository;
        this.applicationProperties = applicationProperties;
    }

    public Optional<String> getPresignedPutUrl(Long blogId, String filename) {
        String albumBucket = applicationProperties.getMinio().getBuckets().get(Constants.BUCKETS_ALBUM).get("name");
        String prefixFolderPath = generatePrefixFolderPath(blogId);
        String fileFolder = getBlogFileFolder(albumBucket, prefixFolderPath);
        String objectName = Paths.get(prefixFolderPath, fileFolder, filename).toString().replaceAll("\\\\", "/");
        return Optional.of(minioRepository.getPresignedPutUrl(albumBucket, objectName));
    }

    private String getBlogFileFolder(String bucketName, String parentPath) {
        for (int i = 0; i < 5; i++) {
            String fileFolder = minioRepository.getFileFolder(bucketName, parentPath);
            String directory = Paths.get(parentPath, fileFolder).toString().replaceAll("\\\\", "/");
            if (!fileUploadAlbumRepository.findById(directory).isPresent()) {
                FileUploadAlbum fileUploadAlbum = new FileUploadAlbum();
                fileUploadAlbum.setDirectory(directory);
                fileUploadAlbumRepository.save(fileUploadAlbum);
                return fileFolder;
            }
        }
        return null;
    }

    public List<String> getImageList(Long blogId) {
        String albumBucket = applicationProperties.getMinio().getBuckets().get(Constants.BUCKETS_ALBUM).get("name");
        String prefixFolderPath = generatePrefixFolderPath(blogId);
        return minioRepository.getFileList(albumBucket, prefixFolderPath, true);
    }

    private String generatePrefixFolderPath(Long blogId) {
        String userLogin = SecurityUtils.getCurrentUserLogin().get();
        String blogFolder = applicationProperties.getMinio().getFolders().get(Constants.FOLDERS_ALBUM_BLOG);
        String blogSubFolder;
        if (ObjectUtils.isEmpty(blogId)) {
            blogSubFolder = applicationProperties.getMinio().getFolders().get(Constants.FOLDERS_ALBUM_BLOG_DEFAULT);
        } else {
            blogSubFolder = applicationProperties.getMinio().getFolders().get(Constants.FOLDERS_ALBUM_BLOG_BLOG) + "_" + Long.toString(blogId);
        }
        return Paths.get(userLogin, blogFolder, blogSubFolder).toString().replaceAll("\\\\", "/");
    }
}
