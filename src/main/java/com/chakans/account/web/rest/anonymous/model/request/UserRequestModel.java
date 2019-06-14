package com.chakans.account.web.rest.anonymous.model.request;

import javax.validation.constraints.Size;

import com.chakans.account.service.dto.UserDTO;

/**
 * Request Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class UserRequestModel extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public UserRequestModel() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRequestModel{" +
            "} " + super.toString();
    }
}
