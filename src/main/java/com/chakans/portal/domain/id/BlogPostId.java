package com.chakans.portal.domain.id;

import java.io.Serializable;
import java.util.Objects;

/**
 * An id for blog_bost.
 */
public class BlogPostId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long blogId;

    private Long postId;

    public BlogPostId() {
    }

    public BlogPostId(Long blogId, Long postId) {
        this.blogId = blogId;
        this.postId = postId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public Long getPostId() {
        return postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogPostId blogPostId = (BlogPostId) o;
        return (blogId != null ? Objects.equals(blogId, blogPostId.getBlogId()) : blogPostId.getBlogId() != null)
            && (postId != null ? Objects.equals(postId, blogPostId.getPostId()) : blogPostId.getPostId() != null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blogId, postId);
    }
}
