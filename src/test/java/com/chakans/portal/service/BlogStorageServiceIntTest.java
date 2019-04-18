package com.chakans.portal.service;

import com.chakans.blog.service.BlogStorageService;
import com.chakans.portal.ChakansApp;
import com.chakans.portal.config.ApplicationProperties;
import com.chakans.portal.repository.FileUploadAlbumRepository;
import com.chakans.portal.repository.MinioRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChakansApp.class)
public class BlogStorageServiceIntTest {

    @Autowired
    private MinioRepository minioRepository;

    @Autowired
    private FileUploadAlbumRepository blogFileUploadRepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    private BlogStorageService blogStorageService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        blogStorageService = new BlogStorageService(minioRepository, blogFileUploadRepository, applicationProperties);
    }

    @Test
    public void testGetMyImagePresignedPutUrl() {
    	String filename = "image.png";
    	blogStorageService.getPresignedPutUrl(null, filename);
    }
}
