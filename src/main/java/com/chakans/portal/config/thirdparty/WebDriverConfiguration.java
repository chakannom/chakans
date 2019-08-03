package com.chakans.portal.config.thirdparty;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.chakans.core.config.thirdparty.webdriver.WebDriverConfigurationHelper;

import io.github.jhipster.config.JHipsterConstants;

/**
 * Basic WebDriver(Selenium) configuration.
 *
 * <p>
 * Creates the beans necessary to manage Connections to webdriver services.
 */
@Configuration
@ConditionalOnProperty(name = "thirdparty.webdriver", matchIfMissing = true)
public class WebDriverConfiguration {

    private final Logger log = LoggerFactory.getLogger(WebDriverConfiguration.class);

    public WebDriverConfiguration() {
    }

    /**
     * Open the TCP port for the chrome headless server, so it is available remotely.
     * @throws RuntimeException if unprocessed operation system
     * @throws IOException if the server failed to start
     */
    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public void chromeHeadlessServer() {
        WebDriverConfigurationHelper.createServer("chrome");
    }
}
