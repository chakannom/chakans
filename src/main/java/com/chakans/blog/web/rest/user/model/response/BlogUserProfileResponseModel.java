package com.chakans.blog.web.rest.user.model.response;

import java.time.Instant;

import com.chakans.blog.service.dto.BlogUserProfileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogUserProfileResponseModel {
    
    @JsonProperty(value = "id")
    private final String userLogin;
    
    private final String nickname;
    
    private final String email;
    
    private final String imageUrl;
    
    private final boolean openedProfile;
    
    private final boolean openedEmail;
    
    private Instant createdDate;
    
    private Instant modifiedDate;

    public BlogUserProfileResponseModel(BlogUserProfileDTO blogUserProfileDTO) {
        this.userLogin = blogUserProfileDTO.getBlogUserProfile().getUserLogin();
        this.nickname = blogUserProfileDTO.getBlogUserProfile().getNickname();
        this.email = blogUserProfileDTO.getBlogUserProfile().getEmail();
        this.imageUrl = blogUserProfileDTO.getBlogUserProfile().getImageUrl();
        this.openedProfile = blogUserProfileDTO.getBlogUserProfile().isOpenedProfile();
        this.openedEmail = blogUserProfileDTO.getBlogUserProfile().isOpenedEmail();
        this.createdDate = blogUserProfileDTO.getBlogUserProfile().getCreatedDate();
        this.modifiedDate = blogUserProfileDTO.getBlogUserProfile().getModifiedDate();
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isOpenedProfile() {
        return openedProfile;
    }

    public boolean isOpenedEmail() {
        return openedEmail;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }
}
