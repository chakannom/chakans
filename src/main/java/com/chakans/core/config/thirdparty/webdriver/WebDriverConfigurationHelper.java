package com.chakans.core.config.thirdparty.webdriver;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import javax.transaction.SystemException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chakans.core.tools.RunProcess;

/**
 * Utility class to configure WebDriver in development.
 *
 * We don't want to include WebDriver when we are packaging for the "prod" profile and won't
 * actually need it, so we have to load / invoke things at runtime through reflection.
 */
public class WebDriverConfigurationHelper {
    
    private static final Logger log = LoggerFactory.getLogger(WebDriverConfigurationHelper.class);
    
    public static RunProcess createServer() {
        return createServer("chrome");
    }
    
    public static RunProcess createServer(String browserName) {
        try {
            log.debug("Starting {} headless server", browserName);
            String workingDirectory =  Paths.get(System.getProperty("user.dir"), "thirdparty/webdriver").toString();
            String webdriverCommend = getCommend(workingDirectory, browserName);
            RunProcess webdriverProcess = new RunProcess(webdriverCommend);
            webdriverProcess.run(false);
            webdriverProcess.getRunningProcess().waitFor(5, TimeUnit.SECONDS);
            log.debug("Started {} headless server", browserName);
            return webdriverProcess;
        } catch (SystemException e) {
            throw new RuntimeException("It can not run " + browserName +" headless server under your operation system", e);
        }  catch (InterruptedException e) {
            throw new RuntimeException("The " + browserName + "'s thread is interrupted", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create " + browserName + " headless server", e);
        }
    }
    
    private static String getCommend(String workingDirectory, String browserName) throws SystemException {
        String osFolderName;
        String webdriverexe;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0) {
            osFolderName = "windows";
            webdriverexe = "chromedriver.exe";
        } else if (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0) {
            osFolderName = "linux";
            webdriverexe = "chromedriver";
        } else if (osName.indexOf("mac") >= 0) {
            osFolderName = "mac";
            webdriverexe = "chromedriver";
        } else {
            throw new SystemException(osName);
        }
        return Paths.get(workingDirectory, osFolderName, webdriverexe).toString();
    }

}
