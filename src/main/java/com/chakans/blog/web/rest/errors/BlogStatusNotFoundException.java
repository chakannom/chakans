package com.chakans.blog.web.rest.errors;

import com.chakans.core.web.rest.errors.ErrorConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BlogStatusNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BlogStatusNotFoundException() {
        super(ErrorConstants.BLOG_INVALID_BLOG_STATUS_TYPE, "Incorrect status of blog", Status.BAD_REQUEST);
    }
}
