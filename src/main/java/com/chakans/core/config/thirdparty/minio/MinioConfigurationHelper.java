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

import com.chakans.core.tools.RunProcess;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;

import javax.transaction.SystemException;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to configure Minio in development.
 *
 * We don't want to include Minio when we are packaging for the "prod" profile and won't
 * actually need it, so we have to load / invoke things at runtime through reflection.
 */
public class MinioConfigurationHelper {

    private static final Logger log = LoggerFactory.getLogger(MinioConfigurationHelper.class);

    public static RunProcess createServer(Map<String, String> buckets) {
        return createServer("http://localhost:9000/", "storage1", "storage1", false, buckets);
    }

    public static RunProcess createServer(String endPoint, String accessKey, String secretKey, boolean secure, Map<String, String> buckets) {
        try {
            log.debug("Starting Minio storage server");
            String workingDirectory =  Paths.get(System.getProperty("user.dir"), "thirdparty/minio").toString();
            String minioCommend = getCommend(workingDirectory);
            String configDirectory = Paths.get(workingDirectory, "config").toString();
            String dataDirectory = Paths.get(System.getProperty("user.dir"), "build/minio").toString();
            RunProcess minioProcess = new RunProcess(minioCommend);
            minioProcess.run(false, "server", "--config-dir", configDirectory, dataDirectory);
            minioProcess.getRunningProcess().waitFor(5, TimeUnit.SECONDS);
            log.debug("Config dir: " + configDirectory);
            log.debug("Data dir: " + dataDirectory);
            log.debug("Started Minio storage server");
            if (buckets != null && buckets.size() > 0) {
                createBuckets(endPoint, accessKey, secretKey, secure, buckets);
            }
            return minioProcess;
        } catch (SystemException e) {
            throw new RuntimeException("It can not run minio storage server under your operation system", e);
        }  catch (InterruptedException e) {
            throw new RuntimeException("The minio's thread is interrupted", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create minio server", e);
        }
    }

    private static String getCommend(String workingDirectory) throws SystemException {
        String osFolderName;
        String minioexe;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0) {
            osFolderName = "windows";
            minioexe = "minio.exe";
        } else if (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0) {
            osFolderName = "linux";
            minioexe = "minio";
        } else if (osName.indexOf("mac") >= 0) {
            osFolderName = "mac";
            minioexe = "minio";
        } else {
            throw new SystemException(osName);
        }
        return Paths.get(workingDirectory, osFolderName, minioexe).toString();
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
