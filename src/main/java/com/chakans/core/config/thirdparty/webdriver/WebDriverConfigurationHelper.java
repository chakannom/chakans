package com.chakans.core.config.thirdparty.webdriver;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chakans.core.config.thirdparty.docker.DockerHelper;
import com.github.dockerjava.api.exception.DockerClientException;

/**
 * Utility class to configure WebDriver in development.
 *
 * We don't want to include WebDriver when we are packaging for the "prod" profile and won't
 * actually need it, so we have to load / invoke things at runtime through reflection.
 */
public class WebDriverConfigurationHelper {

    private static final Logger log = LoggerFactory.getLogger(WebDriverConfigurationHelper.class);

    private static final String imageName = "selenium/standalone-{browserName}-debug";

    private static final String tag = "3.141.59";

    private static final String containerName = "dev-webdriver-{browserName}";

    private static final Integer hostPort = 9902;

    private static final Integer hostVncPort = 5990;

    public static boolean createServer() {
        return createServer("chrome");
    }

    public static boolean createServer(String browserName) {
        try {
            log.debug("Starting {} webdriver's docker container", browserName);
            DockerHelper dockerHelper = new DockerHelper(imageName.replace("{browserName}", browserName), tag) {};
    		if (!dockerHelper.isExistedImage()) {
    			dockerHelper.pullImage();
    		}
    		if (!dockerHelper.isExistedRunningContainer(containerName.replace("{browserName}", browserName))) {
                dockerHelper.removeContainer(containerName.replace("{browserName}", browserName));
    			Map<String, String> volumes = new HashMap<>();
    			volumes.put("/dev/shm", "/dev/shm");
    			Map<Integer, Integer> ports = new HashMap<>();
    			ports.put(hostPort, 4444);
    			ports.put(hostVncPort, 5900);
    			dockerHelper.runContainer(null, volumes, ports, containerName.replace("{browserName}", browserName), null);
    		}
            log.debug("Started {} webdriver's docker container", browserName);
		} catch (DockerClientException e) {
			log.debug("Failed to start {} webdriver's docker container", browserName);
			return false;
		}
        return true;
    }
}
