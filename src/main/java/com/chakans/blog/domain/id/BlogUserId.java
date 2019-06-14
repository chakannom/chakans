package com.chakans.blog.domain.id;

import java.io.Serializable;
import java.util.Objects;

/**
 * An id for blog_user.
 */
public class BlogUserId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long blogId;

    private String userLogin;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogUserId)) return false;

        BlogUserId blogUserId = (BlogUserId) o;
        return Objects.equals(blogId, blogUserId.getBlogId())
            && Objects.equals(userLogin, blogUserId.getUserLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(blogId, userLogin);
    }
}
