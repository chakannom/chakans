package com.chakans.blog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.chakans.blog.domain.id.BlogUserId;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * A blog_user.
 */
@Entity
@IdClass(BlogUserId.class)
@Table(name = "cks_blog_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "blog_id", nullable = false, updatable = false)
    private Long blogId;

    @Id
    @Size(min = 1, max = 50)
    @Column(name = "user_login", length = 50, nullable = false, updatable = false)
    private String userLogin;

    @OneToOne
    @JoinColumn(name = "blog_authority_name", referencedColumnName = "name")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private BlogAuthority blogAuthority;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public BlogAuthority getBlogAuthority() {
        return blogAuthority;
    }

    public void setBlogAuthority(BlogAuthority blogAuthority) {
        this.blogAuthority = blogAuthority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogUser)) return false;

        BlogUser blogUser = (BlogUser) o;
        return Objects.equals(blogId, blogUser.getBlogId())
            && Objects.equals(userLogin, blogUser.getUserLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(blogId, userLogin);
    }

    @Override
    public String toString() {
        return "BlogPost{" +
            "blogId=" + blogId +
            ", userLogin='" + userLogin + '\'' +
            ", blogAuthority=" + blogAuthority +
            "}";
    }
}
