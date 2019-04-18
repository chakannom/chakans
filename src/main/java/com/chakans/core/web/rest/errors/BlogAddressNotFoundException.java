package com.chakans.core.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BlogAddressNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BlogAddressNotFoundException() {
        super(ErrorConstants.BLOG_ADDRESS_ALREADY_USED_TYPE, "Address not registered", Status.BAD_REQUEST);
    }
}
