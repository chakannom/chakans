package com.chakans.portal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Properties specific to Chakans.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Filters filters = new Filters();
    
    private final GracefulShutdown gracefulShutdown = new GracefulShutdown();

    private final UrlSecurity urlSecurity = new UrlSecurity();

    private final Minio minio = new Minio();

    private final Blog blog = new Blog();

    public Filters getFilters() {
        return filters;
    }

    public GracefulShutdown getGracefulShutdown() {
        return gracefulShutdown;
    }

    public UrlSecurity getUrlSecurity() {
        return urlSecurity;
    }

    public Minio getMinio() {
        return minio;
    }

    public Blog getBlog() {
        return blog;
    }

    public static class Filters {

        private final HtmlRender htmlRender = new HtmlRender();

        public HtmlRender getHtmlRender() {
            return htmlRender;
        }

        public static class HtmlRender {

            private final Map<String, String> remoteAddresses = new HashMap<>();

            private String token = "";
            
            public Map<String, String> getRemoteAddresses() {
                return remoteAddresses;
            }
            
            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }

    public static class GracefulShutdown {

        private long shutdownTimeoutSeconds = 10;

        public long getShutdownTimeoutSeconds() {
            return shutdownTimeoutSeconds;
        }

        public void setShutdownTimeoutSeconds(long shutdownTimeoutSeconds) {
            this.shutdownTimeoutSeconds = shutdownTimeoutSeconds;
        }
    }

    public static class UrlSecurity {

        private String[] permitApisPaths = {"/**"};

        public String[] getPermitApisPaths() {
            return permitApisPaths;
        }

        public void setPermitApisPaths(String[] permitApisPaths) {
            this.permitApisPaths = permitApisPaths;
        }
    }

    public static class Minio {

        private String endPoint = "http://localhost:9000";

        private String accessKey = "";

        private String secretKey = "";

        private boolean secure = false;

        private final Map<String, Map<String, String>> buckets = new HashMap<>();

        private final Map<String, String> folders = new HashMap<>();

        private String webRpcUrl = "http://localhost:9000/minio/webrpc";

        public String getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(String endPoint) {
            this.endPoint = endPoint;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public boolean isSecure() {
            return secure;
        }

        public void setSecure(boolean secure) {
            this.secure = secure;
        }

        public Map<String, Map<String, String>> getBuckets() {
            return buckets;
        }

        public Map<String, String> getFolders() {
            return folders;
        }

        public String getWebRpcUrl() {
            return webRpcUrl;
        }

        public void setWebRpcUrl(String webRpcUrl) {
            this.webRpcUrl = webRpcUrl;
        }
    }

    public static class Blog {

        private String domain = "localhost";

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }
}