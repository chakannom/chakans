package com.chakans.blog.web.rest.user.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogPostTagResponseModel {
    
    private final String name;
    
    private final Integer postCount;
    
    public BlogPostTagResponseModel(String name, List<Long> postIds, boolean showPostCount) {
        this.name = name;
        this.postCount = showPostCount ? postIds.size() : null;
    }

    public String getName() {
        return name;
    }

    public Integer getPostCount() {
        return postCount;
    }
}
