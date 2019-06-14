package com.chakans.portal.config;

import com.chakans.core.config.shutdown.UndertowGracefulShutdownHandlerWrapper;
import com.chakans.core.filters.htmlrender.HtmlRenderFilter;
import com.chakans.portal.config.filter.UrlRewriteFilter;
import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.h2.H2ConfigurationHelper;
import io.github.jhipster.web.filter.CachingHttpHeadersFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.*;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

import static java.net.URLDecoder.decode;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    private final JHipsterProperties jHipsterProperties;

    private final ApplicationProperties applicationProperties;

    public WebConfigurer(Environment env, JHipsterProperties jHipsterProperties, ApplicationProperties applicationProperties) {
        this.env = env;
        this.jHipsterProperties = jHipsterProperties;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
        initHtmlRenderFilter(servletContext, disps);
        initUrlRewriteFilter(servletContext, disps);
        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_PRODUCTION))) {
            initCachingHttpHeadersFilter(servletContext, disps);
        }
        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT))) {
            initH2Console(servletContext);
        }
        log.info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(WebServerFactory server) {
        setMimeMappings(server);
        // When running in an IDE or with ./gradlew bootRun, set location of the static web assets.
        setLocationForStaticAssets(server);
        // Enable graceful shutdown for Undertow
        setGracefulShutdown(server);
    }

    private void setMimeMappings(WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory) {
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
            mappings.add("html", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
            // CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
            mappings.add("json", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
            ConfigurableServletWebServerFactory servletWebServer = (ConfigurableServletWebServerFactory) server;
            servletWebServer.setMimeMappings(mappings);
        }
    }

    private void setLocationForStaticAssets(WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory) {
            ConfigurableServletWebServerFactory servletWebServer = (ConfigurableServletWebServerFactory) server;
            File root;
            String prefixPath = resolvePathPrefix();
            root = new File(prefixPath + "build/resources/main/static/");
            if (root.exists() && root.isDirectory()) {
                servletWebServer.setDocumentRoot(root);
            }
        }
    }

    private void setGracefulShutdown(WebServerFactory server) {
        if (server instanceof UndertowServletWebServerFactory) {
            ((UndertowServletWebServerFactory) server)
                .addDeploymentInfoCustomizers(builder ->
                    builder.addInitialHandlerChainWrapper(undertowGracefulShutdownHandlerWrapper()));
        }
    }

    /**
     * Resolve path prefix to static resources.
     */
    private String resolvePathPrefix() {
        String fullExecutablePath;
        try {
            fullExecutablePath = decode(this.getClass().getResource("").getPath(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            /* try without decoding if this ever happens */
            fullExecutablePath = this.getClass().getResource("").getPath();
        }
        String rootPath = Paths.get(".").toUri().normalize().getPath();
        String extractedPath = fullExecutablePath.replace(rootPath, "");
        int extractionEndIndex = extractedPath.indexOf("build/");
        if (extractionEndIndex <= 0) {
            return "";
        }
        return extractedPath.substring(0, extractionEndIndex);
    }

    /**
     * Initializes the caching HTTP Headers Filter.
     */
    private void initCachingHttpHeadersFilter(ServletContext servletContext,
                                              EnumSet<DispatcherType> disps) {
        log.debug("Registering Caching HTTP Headers Filter");
        FilterRegistration.Dynamic cachingHttpHeadersFilter =
            servletContext.addFilter("cachingHttpHeadersFilter",
                new CachingHttpHeadersFilter(jHipsterProperties));

        cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/i18n/*");
        cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/content/*");
        cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/app/*");
        cachingHttpHeadersFilter.setAsyncSupported(true);
    }

    /**
     * Initializes HtmlRenderFilter.
     */
    private void initHtmlRenderFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.debug("Initializing HtmlRenderFilter registries");
        FilterRegistration.Dynamic htmlRenderFilter =
                servletContext.addFilter("htmlRenderFilter", new HtmlRenderFilter());

        String remoteAddressForChromeDriver = applicationProperties.getFilters().getHtmlRender().getRemoteAddresses().get("chrome-driver");
        String token = applicationProperties.getFilters().getHtmlRender().getToken();

        htmlRenderFilter.setInitParameter("remoteAddressForChromeDriver", remoteAddressForChromeDriver);
        htmlRenderFilter.setInitParameter("token", token);

//        htmlRenderFilter.addMappingForUrlPatterns(disps, true, "/*");
        htmlRenderFilter.addMappingForUrlPatterns(disps, true, "/apis/blog/v1/blogs/blog");
        htmlRenderFilter.setAsyncSupported(true);
    }

    /**
     * Initializes UrlRewriteFilter.
     */
    private void initUrlRewriteFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.debug("Initializing UrlRewriteFilter registries");
        FilterRegistration.Dynamic urlRewriteFilter =
            servletContext.addFilter("urlRewriteFilter", new UrlRewriteFilter());

        urlRewriteFilter.setInitParameter("confPath", "url-rewrite.xml");
        urlRewriteFilter.setInitParameter("confReloadLastCheck", "-1");
        urlRewriteFilter.setInitParameter("logLevel", "WARM");

        urlRewriteFilter.addMappingForUrlPatterns(disps, true, "/*");
        urlRewriteFilter.setAsyncSupported(true);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = jHipsterProperties.getCors();
        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/apis/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
        }
        return new CorsFilter(source);
    }

    @Bean
    public UndertowGracefulShutdownHandlerWrapper undertowGracefulShutdownHandlerWrapper() {
        long shutdownTimeoutSeconds = applicationProperties.getGracefulShutdown().getShutdownTimeoutSeconds();
        log.info("Graceful shutdown timeout seconds: {}", shutdownTimeoutSeconds);
        return new UndertowGracefulShutdownHandlerWrapper(shutdownTimeoutSeconds);
    }

    /**
     * Initializes H2 console.
     */
    private void initH2Console(ServletContext servletContext) {
        log.debug("Initialize H2 console");
        H2ConfigurationHelper.initH2Console(servletContext);
    }

}
