package com.chakans.blog.service.dto;

import com.chakans.blog.domain.Blog;
import com.chakans.blog.domain.BlogDesign;

public class BlogDTO {

    private final Blog blog;

    private final BlogDesign blogDesign;

    public BlogDTO(Blog blog, BlogDesign blogDesign) {
        this.blog = blog;
        this.blogDesign = blogDesign;
    }

    public Blog getBlog() {
        return blog;
    }

    public BlogDesign getBlogDesign() {
        return blogDesign;
    }
}
