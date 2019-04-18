package com.chakans.blog.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import com.chakans.core.web.rest.errors.ErrorConstants;

public class BlogCommentObjectTypeNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BlogCommentObjectTypeNotFoundException() {
        super(ErrorConstants.BLOG_INVALID_COMMENT_OBJECT_TYPE, "Incorrect object type of comment", Status.BAD_REQUEST);
    }
}