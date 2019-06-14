package com.chakans.blog.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import com.chakans.core.web.rest.errors.ErrorConstants;

public class BlogThemeNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BlogThemeNotFoundException() {
        super(ErrorConstants.BLOG_THEME_NOT_FOUND_TYPE, "Blog's theme not found", Status.BAD_REQUEST);
    }
}