package com.chakans.core.web.rest.errors;

public class BoardKeyAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public BoardKeyAlreadyUsedException() {
        super(ErrorConstants.BOARD_KEY_ALREADY_USED_TYPE, "Key already in use", "boardManagement", "usingkey");
    }
}
