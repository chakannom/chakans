package com.chakans.drive.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import com.chakans.core.web.rest.errors.ErrorConstants;

public class ParentNodeNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public ParentNodeNotFoundException() {
        super(ErrorConstants.DRIVE_PARENT_NODE_NOT_FOUND_TYPE, "Parent node not found", Status.BAD_REQUEST);
    }
}
