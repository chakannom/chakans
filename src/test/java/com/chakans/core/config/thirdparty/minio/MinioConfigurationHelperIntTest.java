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
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Test class for the MinioConfigurationHelper.
 *
 * @see MinioConfigurationHelper
 */
public class MinioConfigurationHelperIntTest {

    @Test
    public void testCreateServerWithBuckets() {
        RunProcess process = MinioConfigurationHelper.createServer(ImmutableMap.of("test", "test"));
        assertNotNull(process.getRunningProcess());
        assertTrue(process.getRunningProcess().isAlive());
        process.kill();
    }

    @Test
    public void testCreateServerWithEndPointAndAccessKeyAndSecretKeyAndSecureAndBuckets() {
        RunProcess process = MinioConfigurationHelper.createServer("http://localhost:9000/","storage1", "storage1", false, ImmutableMap.of("test", "test"));
        assertNotNull(process.getRunningProcess());
        assertTrue(process.getRunningProcess().isAlive());
        process.kill();
    }

    @Test
    public void testGetCommend() {
        String workingDirectory = Paths.get(System.getProperty("user.dir"), "thirdparty/minio").toString();
        String commend = ReflectionTestUtils.invokeMethod(new MinioConfigurationHelper(), "getCommend", workingDirectory);

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0) {
            assertEquals(commend, workingDirectory + "\\windows\\minio.exe");
        } else if (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0) {
            assertEquals(commend, workingDirectory + "/linux/minio");
        } else if (osName.indexOf("mac") >= 0) {
            assertEquals(commend, workingDirectory + "/mac/minio");
        }
    }
}
