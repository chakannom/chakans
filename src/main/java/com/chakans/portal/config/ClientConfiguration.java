package com.chakans.portal.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.chakans.core.config.thirdparty.minio.MinioClientHttpRequestInterceptor;

@Configuration
public class ClientConfiguration {

    private final ApplicationProperties applicationProperties;

    public ClientConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        String endPoint = applicationProperties.getMinio().getEndPoint();
        String accessKey = applicationProperties.getMinio().getAccessKey();
        String secretKey = applicationProperties.getMinio().getSecretKey();
        boolean secure = applicationProperties.getMinio().isSecure();
        return new MinioClient(endPoint, accessKey, secretKey, secure);
    }

    @Bean(name = "minioRpcClient")
    public RestTemplate minioRpcClient() {
        String webRpcUrl = applicationProperties.getMinio().getWebRpcUrl();
        String username = applicationProperties.getMinio().getAccessKey();
        String password = applicationProperties.getMinio().getSecretKey();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new MinioClientHttpRequestInterceptor(webRpcUrl, username, password));
        return restTemplate;
    }
}
