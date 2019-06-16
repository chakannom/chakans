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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chakans.core.config.thirdparty.docker.DockerHelper;
import com.github.dockerjava.api.exception.DockerClientException;

/**
 * Utility class to configure Imgproxy in development.
 *
 * We don't want to include Imgproxy when we are packaging for the "prod" profile and won't
 * actually need it, so we have to load / invoke things at runtime through reflection.
 */
public class ImgproxyConfigurationHelper {

    private static final Logger log = LoggerFactory.getLogger(ImgproxyConfigurationHelper.class);

    private static final String imageName = "darthsim/imgproxy";

    private static final String tag = "v2.2.13";

    private static final String containerName = "dev-imgproxy";

    private static final Integer hostPort = 9901;

    private static final String imgproxyKey = "3a8f347756fa5013430a1a3d0ebe2ad6";

    private static final String imgproxySalt = "19b63d683008e7b88bb4427d9c0b45b3";

    private static final String imgproxyMaxClients = "5";

    public static boolean createServer() {
        try {
            log.debug("Starting Imgproxy's docker container");
            DockerHelper dockerHelper = new DockerHelper(imageName, tag);
    		if (!dockerHelper.isExistedImage()) {
    			dockerHelper.pullImage();
    		}
    		if (!dockerHelper.isExistedRunningContainer(containerName)) {
    		    dockerHelper.removeContainer(containerName);
    			List<String> environments = Arrays.asList("IMGPROXY_KEY=" + imgproxyKey, "IMGPROXY_SALT=" + imgproxySalt, "IMGPROXY_MAX_CLIENTS=" + imgproxyMaxClients);
    			Map<Integer, Integer> ports = new HashMap<>();
    			ports.put(hostPort, 8080);
    			dockerHelper.runContainer(environments, null, ports, containerName, null);
    		}
    		dockerHelper.waitStarted(containerName);
            log.debug("Started Imgproxy's docker container");
		} catch (DockerClientException e) {
			log.debug("Failed to start Imgproxy's docker container");
			return false;
		}
        return true;
    }
}
