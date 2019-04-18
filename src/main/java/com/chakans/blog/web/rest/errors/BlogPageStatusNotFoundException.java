package com.chakans.blog.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import com.chakans.core.web.rest.errors.ErrorConstants;

public class BlogPageStatusNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BlogPageStatusNotFoundException() {
        super(ErrorConstants.BLOG_INVALID_PAGE_STATUS_TYPE, "Incorrect status of page", Status.BAD_REQUEST);
    }
}
