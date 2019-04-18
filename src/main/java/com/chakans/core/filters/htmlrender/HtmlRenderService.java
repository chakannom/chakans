package com.chakans.core.filters.htmlrender;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlRenderService {

    private final Logger log = LoggerFactory.getLogger(HtmlRenderService.class);

    private final String HTMLRENDERTOKEN_KEY = "htmlRenderToken";

    private final HtmlRenderConfig htmlRenderConfig;

    public HtmlRenderService(Map<String, String> config) {
        this.htmlRenderConfig = new HtmlRenderConfig(config);
    }
    
    public boolean isExecutable() {
        return !StringUtils.isEmpty(htmlRenderConfig.getToken());
    }
    
    public boolean isAcceptable(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter(HTMLRENDERTOKEN_KEY);
        if (!token.equals(htmlRenderConfig.getToken())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }

    public boolean execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (handleHtmlRender(request, response)) {
                return true;
            }
        } catch (Exception e) {
            log.error("HtmlRender service error", e);
        }
        return false;
    }

    public void destroy() {
        // TODO Auto-generated method stub

    }

    private boolean handleHtmlRender(HttpServletRequest request, HttpServletResponse response)
        throws IOException, InterruptedException {

        String token = request.getParameter(HTMLRENDERTOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            if (htmlRenderedPageResponse(request, response)) {
                return true;
            }
        }

        return false;
    }

    private boolean htmlRenderedPageResponse(HttpServletRequest request, HttpServletResponse response)
        throws IOException, InterruptedException {

        WebDriver webDriver = null;
        try {
            webDriver = htmlRenderConfig.getRemoteWebDiver(BrowserType.CHROME);
            processRendering(webDriver, request);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=UTF-8");
            String html = webDriver.getPageSource();
            responseEntity(html, response);
            return true;
        } finally {
            webDriver.quit();
        }
    }

    private void processRendering(WebDriver webDriver, HttpServletRequest request) throws InterruptedException {
//            webDriver.manage().addCookie(new Cookie(HTMLRENDERTOKEN_KEY, htmlRenderConfig.getHtmlRenderToken(), getHostname(request), "/", null));

        System.out.println(getFullUrlForRendering(request));
        webDriver.get(getFullUrlForRendering(request));
//        webDriver.navigate().to(getFullUrl(request));

//            WebDriverWait webDriverWait = new WebDriverWait(webDriver, 5);
//            webDriverWait.until((x) -> x.findElement(By.className("sign-in-container")) != null);
        Thread.sleep(5000);

    }

    private String getFullUrlForRendering(HttpServletRequest request) {
        StringBuilder fullUrl = new StringBuilder(request.getRequestURL());
        fullUrl.append("?").append(HTMLRENDERTOKEN_KEY).append("=").append(htmlRenderConfig.getToken());
        if (request.getQueryString() != null) {
            fullUrl.append("&").append(request.getQueryString());
        }
        return fullUrl.toString();
    }

    /**
     * Copy response body data (the entity)
     */
    private void responseEntity(String html, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        try {
            printWriter.write(html);
            printWriter.flush();
        } finally {
            closeQuietly(printWriter);
        }
    }

    protected void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            log.error("Close error", e);
        }
    }
}
