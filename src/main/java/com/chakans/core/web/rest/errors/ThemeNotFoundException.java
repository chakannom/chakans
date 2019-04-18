package com.chakans.core.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ThemeNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public ThemeNotFoundException() {
        super(ErrorConstants.THEME_NOT_FOUND_TYPE, "Theme not found", Status.BAD_REQUEST);
    }
}
