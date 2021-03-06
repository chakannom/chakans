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

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Test class for the MinioConfigurationHelper.
 *
 * @see MinioConfigurationHelper
 */
public class MinioConfigurationHelperIT {

    @Test
    public void testCreateServerWithEndPointAndAccessKeyAndSecretKeyAndSecureAndBuckets() {
        MinioConfigurationHelper.createServer("http://localhost:9900/","storage1", "storage1", false, ImmutableMap.of("test", ImmutableMap.of("name", "test")));
    }
}
