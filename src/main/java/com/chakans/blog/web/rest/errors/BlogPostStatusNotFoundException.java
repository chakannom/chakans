package com.chakans.blog.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import com.chakans.core.web.rest.errors.ErrorConstants;

public class BlogPostStatusNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BlogPostStatusNotFoundException() {
        super(ErrorConstants.BLOG_INVALID_POST_STATUS_TYPE, "Incorrect status of post", Status.BAD_REQUEST);
    }
}
