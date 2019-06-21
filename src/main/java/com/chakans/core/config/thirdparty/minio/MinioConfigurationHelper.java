/*
 * Copyright 2017-2018 the original author or authors from the Chakans project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chakans.core.config.thirdparty.minio;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;

import com.chakans.core.config.thirdparty.docker.DockerHelper;
import com.github.dockerjava.api.exception.DockerClientException;
import com.google.common.collect.ImmutableMap;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidObjectPrefixException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.NoResponseException;
import io.minio.errors.RegionConflictException;

/**
 * Utility class to configure Minio in development.
 *
 * We don't want to include Minio when we are packaging for the "prod" profile and won't
 * actually need it, so we have to load / invoke things at runtime through reflection.
 */
public class MinioConfigurationHelper {

    private static final Logger log = LoggerFactory.getLogger(MinioConfigurationHelper.class);

    private static final String IMAGENAME = "minio/minio";

    private static final String TAG = "RELEASE.2019-06-13T01-41-13Z";

    private static final String CONTAINERNAME = "dev-minio";

    private static final Integer HOSTPORT = 9900;

    private static final String POLICYJSONFORM = "{\"Version\": \"2012-10-17\",\"Statement\": [{\"Effect\": \"Allow\",\"Principal\": {\"AWS\": [\"*\"]},\"Action\": [\"s3:GetBucketLocation\",${BUCKETACTIONS}],\"Resource\": [\"arn:aws:s3:::${BUCKETNAME}\"]},{\"Effect\": \"Allow\",\"Principal\": {\"AWS\": [\"*\"]},\"Action\": [${OBJECTACTIONS}],\"Resource\": [\"arn:aws:s3:::${BUCKETNAME}/*\"]}]}";
    
    private static final Map<String, String> BUCKETACTIONTYPES = ImmutableMap.of("read", "\"s3:ListBucket\"", "write", "\"s3:ListBucketMultipartUploads\"");
    
    private static final Map<String, String> OBJECTACTIONTYPES = ImmutableMap.of("read", "\"s3:GetObject\"", "write", "\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"");

    public static boolean createServer(String endPoint, String accessKey, String secretKey, boolean secure, Map<String, Map<String, String>> buckets) {
        try {
            log.debug("Starting Minio's docker container");
            DockerHelper dockerHelper = new DockerHelper(IMAGENAME, TAG);
            if (!dockerHelper.isExistedImage()) {
                dockerHelper.pullImage();
            }
            if (!dockerHelper.isExistedRunningContainer(CONTAINERNAME)) {
                dockerHelper.removeContainer(CONTAINERNAME);
                List<String> environments = Arrays.asList("MINIO_ACCESS_KEY=" + accessKey, "MINIO_SECRET_KEY=" + secretKey, "MINIO_WORM=on");
                Map<String, String> volumes = new HashMap<>();
                volumes.put(Paths.get(System.getProperty("user.dir"), "build/minio").toString(), "/data");
                Map<Integer, Integer> ports = new HashMap<>();
                ports.put(HOSTPORT, 9000);
                List<String> commands = Arrays.asList("server", "/data");
                dockerHelper.runContainer(environments, volumes, ports, CONTAINERNAME, commands);
            }
            dockerHelper.waitStarted(CONTAINERNAME);
            log.debug("Started Minio's docker container");
            if (buckets != null && buckets.size() > 0) {
                createBuckets(endPoint, accessKey, secretKey, secure, buckets);
            }
        } catch (DockerClientException e) {
            log.debug("Failed to start Minio's docker container");
            return false;
        }
        return true;
    }

    public static void createBuckets(String endPoint, String accessKey, String secretKey, boolean secure, Map<String, Map<String, String>> buckets) {
        try {
            MinioClient minioClient = new MinioClient(endPoint, accessKey, secretKey, secure);
            for (Map.Entry<String, Map<String, String>> bucket : buckets.entrySet()) {
                if (StringUtils.isNotEmpty(bucket.getValue().get("name"))) {
                    if (minioClient.bucketExists(bucket.getValue().get("name"))) {
                        log.debug("Bucket already exists. {}: {}", bucket.getKey(), bucket.getValue().get("name"));
                    } else {
                        minioClient.makeBucket(bucket.getValue().get("name"));
                        log.debug("It makes bucket. {}: {}", bucket.getKey(), bucket.getValue().get("name"));
                        if (isValidPolicy(bucket.getValue().get("policy"))) {
                            String policyJson = getPolicyJson(bucket.getValue().get("name"), bucket.getValue().get("policy").split(","));
                            minioClient.setBucketPolicy(bucket.getValue().get("name"), policyJson);
                            log.debug("It sets policy's bucket. {}: {}", bucket.getKey(), bucket.getValue().get("policy"));
                        }
                    }
                }
            }
        } catch (InvalidEndpointException | InvalidPortException | InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException
                | InsufficientDataException | NoResponseException | ErrorResponseException | InternalException | RegionConflictException
                | InvalidObjectPrefixException | IOException | XmlPullParserException e) {
            log.debug(e.getMessage());
        }
    }
    
    private static boolean isValidPolicy(String policy) {
        if (StringUtils.isEmpty(policy)) {
            return false;
        }
        if (!Arrays.asList(policy.split(",")).stream()
                .anyMatch(action -> action.equalsIgnoreCase("read") || action.equalsIgnoreCase("write"))) {
            return false;
        }
        return true;
    }

    private static String getPolicyJson(String bucket, String[] actions) {
        String bucketActions = "";
        for (String action : actions) {
            if (StringUtils.isNotEmpty(bucketActions)) {
                bucketActions += ",";  
            }
            bucketActions += BUCKETACTIONTYPES.get(action);
        }
       
        String objectActions = "";
        for (String action : actions) {
            if (StringUtils.isNotEmpty(objectActions)) {
                objectActions += ",";  
            }
            objectActions += OBJECTACTIONTYPES.get(action);
        }

        return POLICYJSONFORM.replace("${BUCKETACTIONS}", bucketActions)
                .replace("${OBJECTACTIONS}", objectActions).replace("${BUCKETNAME}", bucket);
    }
}
