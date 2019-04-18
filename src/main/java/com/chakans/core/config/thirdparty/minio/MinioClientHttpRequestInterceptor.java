package com.chakans.core.config.thirdparty.minio;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class MinioClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final String LOGIN_QUERY = "{\"id\":1,\"jsonrpc\":\"2.0\",\"params\":{\"username\":\"%s\",\"password\":\"%s\"},\"method\":\"Web.Login\"}";

    private final String webRpcUrl;

    private final String username;

    private final String password;

    private final RestTemplate restTemplate = new RestTemplate();

    public MinioClientHttpRequestInterceptor(String webRpcUrl, String username, String password) {
        this.webRpcUrl = webRpcUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        requestWrapper.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        requestWrapper.getHeaders().set("Authorization", "Bearer " + getToken());
        return execution.execute(requestWrapper, body);
    }

    private String getToken() {
        String query = String.format(LOGIN_QUERY, username, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<JsonNode> loginResponse = restTemplate.postForEntity(webRpcUrl, new HttpEntity<>(query, headers), JsonNode.class);
        return loginResponse.getBody().get("result").get("token").asText();
    }
}
