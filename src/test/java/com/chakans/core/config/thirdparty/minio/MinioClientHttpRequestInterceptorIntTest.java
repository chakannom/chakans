package com.chakans.core.config.thirdparty.minio;

import com.chakans.core.tools.RunProcess;
import org.junit.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for the  MinioClientHttpRequestInterceptor.
 *
 * @see  MinioClientHttpRequestInterceptor
 */
public class MinioClientHttpRequestInterceptorIntTest {

    private static RunProcess process;

    private static MinioClientHttpRequestInterceptor minioClientHttpRequestInterceptor;

    @BeforeClass
    public static void beforeClass() {
        process = MinioConfigurationHelper.createServer(null);
        minioClientHttpRequestInterceptor = new MinioClientHttpRequestInterceptor("http://localhost:9000/minio/webrpc", "storage1", "storage1");
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
        process.kill();
    }
}
