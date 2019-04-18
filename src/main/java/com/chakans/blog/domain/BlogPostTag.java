package com.chakans.blog.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.chakans.blog.domain.id.BlogPostTagId;

/**
 * A blog_post_tag.
 */
@Entity
@IdClass(BlogPostTagId.class)
@Table(name = "blog_post_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogPostTag implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "post_id", nullable = false, updatable = false)
    private Long postId;

    @Id
    @Column(name = "blog_id", nullable = false, updatable = false)
    private Long blogId;

    @Id
    @Size(min = 1, max = 255)
    @Column(length = 255, nullable = false, updatable = false)
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
        if (o == null || getClass() != o.getClass()) return false;

        BlogPostTag blogPostTag = (BlogPostTag) o;
        return Objects.equals(postId, blogPostTag.getPostId())
            && Objects.equals(blogId, blogPostTag.getBlogId())
            && Objects.equals(name, blogPostTag.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, blogId, name);
    }


    @Override
    public String toString() {
        return "BlogPostTag{" +
            "postId=" + postId +
            ", blogId=" + blogId +
            ", name='" + name + '\'' +
            '}';
    }
}
