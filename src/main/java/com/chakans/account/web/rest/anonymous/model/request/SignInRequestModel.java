package com.chakans.account.web.rest.anonymous.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request Model object for storing a user's credentials.
 */
public class SignInRequestModel {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = UserRequestModel.PASSWORD_MIN_LENGTH, max = UserRequestModel.PASSWORD_MAX_LENGTH)
    private String password;

    private Boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "SignInRequestModel{" +
            "username='" + username + '\'' +
            ", rememberMe=" + rememberMe +
            "}";
    }
}
