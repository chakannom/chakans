package com.chakans.portal.repository;

import com.chakans.portal.ChakansApp;
import com.chakans.portal.config.ApplicationProperties;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.NoResponseException;
import io.minio.errors.RegionConflictException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the MinioRepositoryIntTest class.
 *
 * @see MinioRepositoryIntTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChakansApp.class)
@Transactional
public class MinioRepositoryIntTest {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    @Qualifier("minioRpcClient")
    private RestTemplate minioRpcClient;

    @Autowired
    private ApplicationProperties applicationProperties;


    private MinioRepository minioRepository;

    @Before
    public void setup() throws InvalidEndpointException, InvalidPortException {
    	minioRepository = new MinioRepository(minioClient, minioRpcClient, applicationProperties);
    }

    // @Test
    public void testUpload() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, RegionConflictException, InvalidArgumentException {
        // Check if the bucket already exists.
        boolean isExist = minioClient.bucketExists("album");
        if(isExist) {
            System.out.println("Bucket already exists.");
        } else {
            minioClient.makeBucket("album");
        }
        // Upload the zip file to the bucket with putObject
        minioClient.putObject("album","test/c_blog_logo_128_128.png", "C:\\Users\\chakannom\\Pictures\\c_blog_logo_128_128.png");
        System.out.println("C:\\\\Users\\\\chakannom\\\\Pictures\\\\c_blog_logo_128_128.png is successfully uploaded as asiaphotos.zip to `asiatrip` bucket.");
    }

    @Test
    public void testWebRPCLogin() {
    	String username = applicationProperties.getMinio().getAccessKey();
    	String password = applicationProperties.getMinio().getSecretKey();
    	System.out.println(username);
    }

}
