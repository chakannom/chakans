package com.chakans.blog.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import com.chakans.core.web.rest.errors.ErrorConstants;

public class BlogCommentStatusNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BlogCommentStatusNotFoundException() {
        super(ErrorConstants.BLOG_INVALID_COMMENT_STATUS_TYPE, "Incorrect status of comment", Status.BAD_REQUEST);
    }
}
