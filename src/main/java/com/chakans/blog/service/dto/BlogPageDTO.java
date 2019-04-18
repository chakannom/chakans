package com.chakans.blog.service.dto;

import com.chakans.blog.domain.BlogPage;

public class BlogPageDTO {

    private final BlogPage blogPage;
    
    public BlogPageDTO(BlogPage blogPage) {
        this.blogPage = blogPage;
    }

    public BlogPage getBlogPage() {
        return blogPage;
    }
}
