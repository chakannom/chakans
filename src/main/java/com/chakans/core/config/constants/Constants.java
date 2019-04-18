package com.chakans.core.config.constants;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    public static final String BLOG_URL_REGEX = "^([A-Za-z0-9][A-Za-z0-9-]*)*[A-Za-z0-9].blog.localhost:8080$";
    public static final String BLOG_CUSTOM_URL_REGEX = "^([a-zA-Z0-9-_]+.)*[a-zA-Z0-9][a-zA-Z0-9-_]+.[a-zA-Z]{2,11}?$";
    public static final String BOARD_KEY_REGEX = "^[_A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "ko";

    // Constants for Minio Storage
    public static final String BUCKETS_ALBUM = "album";
    public static final String BUCKETS_THEME = "theme";
    public static final String BUCKETS_DRIVE = "drive";
    public static final String FOLDERS_ALBUM_BLOG = "album-blog";
    public static final String FOLDERS_ALBUM_BLOG_DEFAULT = "album-blog-default";
    public static final String FOLDERS_ALBUM_BLOG_BLOG = "album-blog-blog";
    public static final String FOLDERS_THEME_BLOG = "theme-blog:";

    // Constants for Produces
    //// Account
    public static final String APPLICATION_VND_ACCOUNT_ADMIN_JSON = "application/vnd.account.admin+json";
    public static final String APPLICATION_VND_ACCOUNT_ANONYMOUS_JSON = "application/vnd.account.anonymous+json";
    //// Blog
    public static final String APPLICATION_VND_BLOG_V1_USER_JSON = "application/vnd.blog.v1.user+json";
    //// Drive
    public static final String APPLICATION_VND_DRIVE_V1_USER_JSON = "application/vnd.drive.v1.user+json";


    private Constants() {
    }
}
