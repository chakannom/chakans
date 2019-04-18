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

import com.chakans.core.tools.RunProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.SystemException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to configure Imgproxy in development.
 *
 * We don't want to include Imgproxy when we are packaging for the "prod" profile and won't
 * actually need it, so we have to load / invoke things at runtime through reflection.
 */
public class ImgproxyConfigurationHelper {

    private static final Logger log = LoggerFactory.getLogger(ImgproxyConfigurationHelper.class);

    public static RunProcess createServer() {
        return createServer(9001);
    }

    public static RunProcess createServer(int port) {
        try {
            log.debug("Starting Imgproxy storage server");
            String workingDirectory = Paths.get(System.getProperty("user.dir"), "thirdparty/imgproxy").toString();
            String imgproxyCommend = getCommend(workingDirectory);
            String keyFile = Paths.get(workingDirectory, "config/key").toString();
            String saltFile = Paths.get(workingDirectory, "config/salt").toString();
            Map<String, String> env = new HashMap<>();
            env.put("PORT", Integer.toString(port, 10));
            RunProcess imgproxyProcess = new RunProcess(imgproxyCommend);
            imgproxyProcess.run(false, env, "-keypath", keyFile, "-saltpath", saltFile);
            imgproxyProcess.getRunningProcess().waitFor(5, TimeUnit.SECONDS);
            log.debug("Started Imgproxy storage server");
            return imgproxyProcess;
        } catch (SystemException e) {
            throw new RuntimeException("It can not run imgproxy storage server under your operation system", e);
        }  catch (InterruptedException e) {
            throw new RuntimeException("The imgproxy's thread is interrupted", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create imgproxy server", e);
        }
    }

    private static String getCommend(String workingDirectory) throws SystemException {
        String osFolderName;
        String imgproxyexe;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0) {
            osFolderName = "windows";
            imgproxyexe = "imgproxy.exe";
        } else if (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0) {
            osFolderName = "linux";
            imgproxyexe = "imgproxy";
        } else if (osName.indexOf("mac") >= 0) {
            osFolderName = "mac";
            imgproxyexe = "imgproxy";
            // It need an executable file for the Mac.
            // It need to compile imgproxy on your Mac.
            throw new SystemException(osName);
        } else {
            throw new SystemException(osName);
        }
        return Paths.get(workingDirectory, osFolderName, imgproxyexe).toString();
    }
}
