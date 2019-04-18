package com.chakans.blog.service.dto;

import com.chakans.blog.domain.BlogUserProfile;

public class BlogUserProfileDTO {
    
    private final BlogUserProfile blogUserProfile;

    public BlogUserProfileDTO(BlogUserProfile blogUserProfile) {
        this.blogUserProfile = blogUserProfile;
    }

    public BlogUserProfile getBlogUserProfile() {
        return blogUserProfile;
    }
}
