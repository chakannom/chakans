package com.chakans.portal.config.thirdparty;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.task.TaskExecutor;

import com.chakans.core.config.thirdparty.minio.MinioConfigurationHelper;
import com.chakans.portal.config.ApplicationProperties;

import io.github.jhipster.config.JHipsterConstants;

/**
 * Basic Minio configuration.
 *
 * <p>
 * Creates the beans necessary to manage Connections to minio services.
 */
@Configuration
public class MinioConfiguration {

    private final Logger log = LoggerFactory.getLogger(MinioConfiguration.class);

    private final Environment env;

    private final ApplicationProperties applicationProperties;

    private final TaskExecutor taskExecutor;

    public MinioConfiguration(Environment env, ApplicationProperties applicationProperties,
                              @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        this.env = env;
        this.applicationProperties = applicationProperties;
        this.taskExecutor = taskExecutor;
    }

    /**
     * Open the TCP port for the minio storage server, so it is available remotely.
     * @throws RuntimeException if unprocessed operation system
     * @throws IOException if the server failed to start
     */
    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public void minioStorageServer() {
        String endPoint = applicationProperties.getMinio().getEndPoint();
        String accessKey = applicationProperties.getMinio().getAccessKey();
        String secretKey = applicationProperties.getMinio().getSecretKey();
        boolean secure = applicationProperties.getMinio().isSecure();
        Map<String, String> buckets =  applicationProperties.getMinio().getBuckets();
        MinioConfigurationHelper.createServer(endPoint, accessKey, secretKey, secure, buckets);
    }

    @PostConstruct
    public void afterPropertiesSet() {
        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_PRODUCTION))) {
            // Check if the bucket already exists.
            taskExecutor.execute(() -> {
                String endPoint = applicationProperties.getMinio().getEndPoint();
                String accessKey = applicationProperties.getMinio().getAccessKey();
                String secretKey = applicationProperties.getMinio().getSecretKey();
                boolean secure = applicationProperties.getMinio().isSecure();
                Map<String, String> buckets = applicationProperties.getMinio().getBuckets();
                MinioConfigurationHelper.createBuckets(endPoint, accessKey, secretKey, secure, buckets);
            });
        }
    }
}