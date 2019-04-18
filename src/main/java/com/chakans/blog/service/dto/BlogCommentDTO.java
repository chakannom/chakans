package com.chakans.blog.service.dto;

import com.chakans.blog.domain.BlogComment;

public class BlogCommentDTO {
    
    private final BlogComment blogComment;

    private final BlogComment parentBlogComment;

    private final Object blogObject;

    public BlogCommentDTO(BlogComment blogComment, BlogComment parentBlogComment, Object blogObject) {
        this.blogComment = blogComment;
        this.parentBlogComment = parentBlogComment;
        this.blogObject = blogObject;
    }

    public BlogComment getBlogComment() {
        return blogComment;
    }

    public BlogComment getParentBlogComment() {
        return parentBlogComment;
    }

    public Object getBlogObject() {
        return blogObject;
    }
}
