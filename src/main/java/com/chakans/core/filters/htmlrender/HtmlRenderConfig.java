package com.chakans.core.filters.htmlrender;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HtmlRenderConfig {

    private final List<String> CHROME_ARGUMENTS = Lists.newArrayList("--no-sandbox", "--ignore-ssl-errors=true", "--ssl-protocol=any");

    private final Map<String, String> config;

    public HtmlRenderConfig(Map<String, String> config) {
        this.config = config;
    }

    public WebDriver getRemoteWebDiver(String browserType) throws MalformedURLException {
        return new RemoteWebDriver(getRemoteAddress(browserType), getCapabilities(browserType));
    }

    public String getToken() {
        return config.get("token");
    }

    private URL getRemoteAddress(String browserType) throws MalformedURLException {
        String remoteAddress = null;
        remoteAddress = config.get("remoteAddressForChromeDriver");
        return new URL(remoteAddress);
    }

    private Capabilities getCapabilities(String browserType) {
        Capabilities capabilities = null;
        capabilities = new ChromeOptions().setHeadless(true).setAcceptInsecureCerts(true).addArguments(CHROME_ARGUMENTS);
        return capabilities;
    }
}
