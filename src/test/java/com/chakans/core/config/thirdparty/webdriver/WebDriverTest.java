package com.chakans.core.config.thirdparty.webdriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverTest {

//    @Test
    public void testChromeRemoteWebDriver() throws InterruptedException, MalformedURLException {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.setAcceptInsecureCerts(true);
        options.addArguments("--ignore-ssl-errors=true");
        options.addArguments("--ssl-protocol=any");
        options.setHeadless(true);
        options.addArguments("--disable-gpu");
//      options.addArguments("--remote-debugging-port=9222");
        options.addArguments("window-size=1400,600");

        WebDriver webDriver = new RemoteWebDriver(new URL("http://localhost:9515"), options);
        webDriver.get("http://www.google.com/");
        webDriver.manage().addCookie(new Cookie("test", "test", "dev.chakans.com", "/", null));
        webDriver.get("https://dev.chakans.com/");
//        Thread.sleep(5000);
        System.out.println(webDriver.getPageSource());
//        webDriver.close();
    }

//    @Test
    public void testChrome() {
        System.setProperty("webdriver.chrome.driver", "/home/development/workspace/chakans/chakans/thirdparty/webdriver/linux/chromedriver");

        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.setAcceptInsecureCerts(true);
        options.addArguments("--ignore-ssl-errors=true");
        options.addArguments("--ssl-protocol=any");
        options.setHeadless(true);
        options.addArguments("--disable-gpu");
//      options.addArguments("--remote-debugging-port=9222");
        options.addArguments("window-size=1400,600");

        WebDriver webDriver = new ChromeDriver(options);
        webDriver.get("http://www.google.com/");
        webDriver.manage().addCookie(new Cookie("test1", "test1", "dev.chakans.com", "/", null));
        webDriver.get("https://dev.chakans.com/");
        
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 5);
        webDriverWait.until((x) -> x.findElement(By.className("sign-in-container")) != null);

        System.out.println(webDriver.getPageSource());
//        webDriver.quit();
    }
}
