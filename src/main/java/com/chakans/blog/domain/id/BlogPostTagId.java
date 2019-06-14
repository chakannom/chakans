package com.chakans.blog.domain.id;

import java.io.Serializable;
import java.util.Objects;

/**
 * An id for blog_post_tag.
 */
public class BlogPostTagId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long postId;

    private Long blogId;

    private String name;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogPostTagId)) return false;

        BlogPostTagId blogPostTagId = (BlogPostTagId) o;
        return Objects.equals(postId, blogPostTagId.getPostId())
            && Objects.equals(blogId, blogPostTagId.getBlogId())
            && Objects.equals(name, blogPostTagId.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, blogId, name);
    }
}
