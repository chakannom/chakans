package com.chakans.blog.web.rest.user.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class BlogUserProfileRequestModel {

    @Size(min = 3, max = 100)
    private String nickname;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 512)
    private String imageUrl;

    private Boolean openedProfile;

    private Boolean openedEmail;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isOpenedProfile() {
        return openedProfile;
    }

    public void setOpenedProfile(Boolean openedProfile) {
        this.openedProfile = openedProfile;
    }

    public Boolean isOpenedEmail() {
        return openedEmail;
    }

    public void setOpenedEmail(Boolean openedEmail) {
        this.openedEmail = openedEmail;
    }

    @Override
    public String toString() {
        return "BlogUserProfileRequestModel{" +
            "nickname='" + nickname + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", openedProfile=" + openedProfile +
            ", openedEmail=" + openedEmail +
            '}';
    }
}
