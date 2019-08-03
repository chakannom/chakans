package com.chakans.blog.service.dto;

import com.chakans.blog.domain.BlogPostTag;

public class BlogPostTagDTO {
    
    private final BlogPostTag blogPostTag;

    public BlogPostTagDTO(BlogPostTag blogPostTag) {
        this.blogPostTag = blogPostTag;
    }

    public BlogPostTag getBlogPostTag() {
        return blogPostTag;
    }
}
