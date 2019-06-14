package com.chakans.blog.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import com.chakans.core.web.rest.errors.ErrorConstants;

public class BlogAddressAlreadyUsedException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BlogAddressAlreadyUsedException() {
        super(ErrorConstants.BLOG_ADDRESS_ALREADY_USED_TYPE, "Address already in use", Status.BAD_REQUEST);
    }
}
