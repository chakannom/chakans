package com.chakans.portal.config.thirdparty;

import com.chakans.core.config.thirdparty.imgproxy.ImgproxyConfigurationHelper;
import io.github.jhipster.config.JHipsterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

/**
 * Basic Imgproxy configuration.
 *
 * <p>
 * Creates the beans necessary to manage Connections to imgproxy services.
 */
@Configuration
public class ImgproxyConfiguration {

    private final Logger log = LoggerFactory.getLogger(ImgproxyConfiguration.class);

    public ImgproxyConfiguration() {
    }

    /**
     * Open the TCP port for the minio storage server, so it is available remotely.
     * @throws RuntimeException if unprocessed operation system
     * @throws IOException if the server failed to start
     */
    @Bean(destroyMethod = "kill")
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public Object imgproxyStorageServer() {
        return ImgproxyConfigurationHelper.createServer();
    }
}
