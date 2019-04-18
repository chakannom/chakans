package com.chakans.portal.repository;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.chakans.portal.config.ApplicationProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import io.minio.Result;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.errors.NoResponseException;

/**
 * An implementation of MinioRepository.
 */
@Repository
public class MinioRepository {

	private final Logger log = LoggerFactory.getLogger(MinioRepository.class);
    // default expiration for a presigned URL is 10 seconds
    private final int DEFAULT_EXPIRY_TIME = 10;

    private final String[] RANDOM_FOLDER_LIST = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private final String SUB_FOLDER_LIST_QUERY = "{\"id\":1,\"jsonrpc\":\"2.0\",\"params\":{\"bucketName\":\"%s\",\"prefix\":\"%s\",\"marker\":\"\"},\"method\":\"Web.ListObjects\"}";;

	private final MinioClient minioClient;

	private final RestTemplate minioRpcClient;

	private final ApplicationProperties applicationProperties;

	public MinioRepository(MinioClient minioClient, @Qualifier("minioRpcClient") RestTemplate minioRpcClient,
                           ApplicationProperties applicationProperties) {
		this.minioClient = minioClient;
		this.minioRpcClient = minioRpcClient;
		this.applicationProperties = applicationProperties;
	}

	public String getPresignedPutUrl(String bucketName, String objectName) {
        try {
			return minioClient.presignedPutObject(bucketName, objectName, DEFAULT_EXPIRY_TIME);
		} catch (InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException
				| InsufficientDataException | NoResponseException | ErrorResponseException | InternalException
				| InvalidExpiresRangeException | IOException | XmlPullParserException e) {
			e.printStackTrace();
		}
        return null;
    }

    public ObjectStat getStatObject(String bucketName, String objectName) {
    	try {
			return minioClient.statObject(bucketName, objectName);
		} catch (InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException
				| NoResponseException | ErrorResponseException | InternalException | IOException
				| XmlPullParserException e) {
			e.printStackTrace();
		}
    	return null;
    }

    public String getFileFolder(String bucketName, String parentPath) {
	    String webRpcUrl = applicationProperties.getMinio().getWebRpcUrl();
        for (int i = 0; i < 5; i++) {
            String fileFolder = UUID.randomUUID().toString().replaceAll("-", "");
            for (int j = 0; j < RANDOM_FOLDER_LIST.length; j++) {
                String randomFolder = RANDOM_FOLDER_LIST[j];
                String query = String.format(SUB_FOLDER_LIST_QUERY, bucketName, parentPath + "/" + randomFolder + "/" + fileFolder + "/");
                ResponseEntity<JsonNode> subFolderListResponse = minioRpcClient.postForEntity(webRpcUrl, new HttpEntity<>(query), JsonNode.class);
                if (subFolderListResponse.getBody().get("result").get("objects") == NullNode.getInstance()) {
                    return randomFolder + "/" + fileFolder;
                }
            }
        }
	    return null;
    }

    public List<String> getFileList(String bucketName, String prefix, boolean recursive) {
	    List<String> fileList = new ArrayList<>();
        try {
            Iterable<Result<Item>> files = minioClient.listObjects(bucketName, prefix, recursive);
            for (Result<Item> file : files) {
                fileList.add(generateFileUri(bucketName, file.get().objectName()));
            }
        } catch (InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | IOException
            | InvalidKeyException | NoResponseException | XmlPullParserException | ErrorResponseException
            | InternalException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    private String generateFileUri(String bucketName, String objectName) {
	    return UriComponentsBuilder.fromUriString(applicationProperties.getMinio().getEndPoint()).pathSegment(bucketName).path(objectName).toUriString();
    }
}
