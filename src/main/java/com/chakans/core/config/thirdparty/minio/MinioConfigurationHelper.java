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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;

import com.chakans.core.config.thirdparty.docker.DockerHelper;
import com.github.dockerjava.api.exception.DockerClientException;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
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

    private static final String imageName = "minio/minio";

    private static final String tag = "RELEASE.2019-06-13T01-41-13Z";

    private static final String containerName = "dev-minio";

    private static final Integer hostPort = 9000;

    public static boolean createServer(String endPoint, String accessKey, String secretKey, boolean secure, Map<String, String> buckets) {
    	try {
    		log.debug("Starting Minio's docker container");
    		DockerHelper dockerHelper = new DockerHelper(imageName, tag);
    		if (!dockerHelper.isExistedImage()) {
    			dockerHelper.pullImage();
    		}
    		if (!dockerHelper.isExistedRunningContainer(containerName)) {
                dockerHelper.removeContainer(containerName);
    			List<String> environments = Arrays.asList("MINIO_ACCESS_KEY=" + accessKey, "MINIO_SECRET_KEY=" + secretKey, "MINIO_WORM=on");
    			Map<String, String> volumes = new HashMap<>();
    			volumes.put(Paths.get(System.getProperty("user.dir"), "build/minio").toString(), "/data");
    			Map<Integer, Integer> ports = new HashMap<>();
    			ports.put(hostPort, 9000);
    			List<String> commands = Arrays.asList("server", "/data");
    			dockerHelper.runContainer(environments, volumes, ports, containerName, commands);
    		}
    		dockerHelper.waitStarted(containerName);
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

    public static void createBuckets(String endPoint, String accessKey, String secretKey, boolean secure, Map<String, String> buckets) {
        try {
            MinioClient minioClient = new MinioClient(endPoint, accessKey, secretKey, secure);
            for (Map.Entry<String, String> bucket : buckets.entrySet()) {
                if (minioClient.bucketExists(bucket.getValue())) {
                    log.debug("Bucket already exists. {}: {}", bucket.getKey(), bucket.getValue());
                } else {
                    minioClient.makeBucket(bucket.getValue());
                    log.debug("It makes bucket. {}: {}", bucket.getKey(), bucket.getValue());
                }
            }
        } catch (InvalidEndpointException | InvalidPortException | InvalidKeyException | NoSuchAlgorithmException
                | NoResponseException | XmlPullParserException | InsufficientDataException | RegionConflictException
                | InvalidBucketNameException | ErrorResponseException | InternalException | IOException e) {
            log.debug(e.getMessage());
        }
    }
}
