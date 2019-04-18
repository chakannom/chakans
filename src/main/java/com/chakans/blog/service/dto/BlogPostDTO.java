package com.chakans.blog.service.dto;

import java.util.List;

import com.chakans.blog.domain.BlogPost;
import com.chakans.blog.domain.BlogPostTag;

public class BlogPostDTO {

    private final BlogPost blogPost;
    
    private final List<BlogPostTag> blogPostTags;
    
    public BlogPostDTO(BlogPost blogPost, List<BlogPostTag> blogPostTags) {
        this.blogPost = blogPost;
        this.blogPostTags = blogPostTags;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public List<BlogPostTag> getBlogPostTags() {
        return blogPostTags;
    }

}
