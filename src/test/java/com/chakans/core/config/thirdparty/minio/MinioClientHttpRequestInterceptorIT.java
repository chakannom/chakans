package com.chakans.core.config.thirdparty.minio;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Test class for the  MinioClientHttpRequestInterceptor.
 *
 * @see  MinioClientHttpRequestInterceptor
 */
public class MinioClientHttpRequestInterceptorIT {

    private static MinioClientHttpRequestInterceptor minioClientHttpRequestInterceptor;

    @BeforeClass
    public static void beforeClass() {
        MinioConfigurationHelper.createServer("http://localhost:9900/", "storage1", "storage1", false, null);
        minioClientHttpRequestInterceptor = new MinioClientHttpRequestInterceptor("http://localhost:9900/minio/webrpc", "storage1", "storage1");
        ReflectionTestUtils.setField(minioClientHttpRequestInterceptor, "restTemplate", new RestTemplate());
    }

    @Test
    public void testGetToken() {
        String token = ReflectionTestUtils.invokeMethod(minioClientHttpRequestInterceptor, "getToken");
        assertNotNull(token);
        assertNotEquals(token, "");
    }

    @AfterClass
    public static void afterClass() {
    }
}
