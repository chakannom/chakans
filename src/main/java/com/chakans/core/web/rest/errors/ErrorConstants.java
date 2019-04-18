package com.chakans.core.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "http://localhost:8080/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI PARAMETERIZED_TYPE = URI.create(PROBLEM_BASE_URL + "/parameterized");
    public static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found");
    public static final URI BLOG_ADDRESS_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/blog-address-already-used");
    public static final URI BLOG_ADDRESS_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/blog-address-not-found");
    public static final URI BLOG_INVALID_BLOG_STATUS_TYPE = URI.create(PROBLEM_BASE_URL + "/blog-invalid-blog-status");
    public static final URI BLOG_INVALID_PAGE_STATUS_TYPE = URI.create(PROBLEM_BASE_URL + "/blog-invalid-page-status");
    public static final URI BLOG_INVALID_POST_STATUS_TYPE = URI.create(PROBLEM_BASE_URL + "/blog-invalid-post-status");
    public static final URI BLOG_INVALID_COMMENT_STATUS_TYPE = URI.create(PROBLEM_BASE_URL + "/blog-invalid-comment-status");
    public static final URI BLOG_INVALID_COMMENT_OBJECT_TYPE = URI.create(PROBLEM_BASE_URL + "/blog-invalid-comment-object");
    public static final URI BLOG_INVALID_THEME_STATUS_TYPE = URI.create(PROBLEM_BASE_URL + "/blog-invalid-theme-status");
    public static final URI THEME_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/theme-not-found");
    public static final URI BOARD_KEY_ALREADY_USED_TYPE  = URI.create(PROBLEM_BASE_URL + "/board-key-already-used");
    public static final URI DRIVE_PARENT_NODE_NOT_FOUND_TYPE  = URI.create(PROBLEM_BASE_URL + "/drive-parent-node-not-found");

    private ErrorConstants() {
    }
}
