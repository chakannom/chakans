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
package com.chakans.core.config.thirdparty.imgproxy;

import com.chakans.core.config.thirdparty.minio.MinioConfigurationHelper;
import com.chakans.core.tools.RunProcess;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the ImgproxyConfigurationHelper.
 *
 * @see ImgproxyConfigurationHelper
 */
public class ImgproxyConfigurationHelperIntTest {

    @Test
    public void testCreateServer() {
        RunProcess process = ImgproxyConfigurationHelper.createServer();
        assertNotNull(process.getRunningProcess());
        assertTrue(process.getRunningProcess().isAlive());
        process.kill();
    }

    @Test
    public void testCreateServerWithPort() {
        RunProcess process = ImgproxyConfigurationHelper.createServer(9002);
        assertNotNull(process.getRunningProcess());
        assertTrue(process.getRunningProcess().isAlive());
        process.kill();
    }

    @Test
    public void testGetCommend() {
        String workingDirectory = Paths.get(System.getProperty("user.dir"), "thirdparty/imgproxy").toString();
        String commend = ReflectionTestUtils.invokeMethod(new ImgproxyConfigurationHelper(), "getCommend", workingDirectory);

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0) {
            assertEquals(commend, workingDirectory + "\\windows\\imgproxy.exe");
        } else if (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0) {
            assertEquals(commend, workingDirectory + "/linux/imgproxy");
        }
    }
}
