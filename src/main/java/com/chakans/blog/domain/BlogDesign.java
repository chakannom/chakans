package com.chakans.blog.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A blog_design.
 */
@Entity
@Table(name = "cks_blog_design")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogDesign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "blog_id", updatable = false)
    private Long blogId;

    @NotNull
    @Column(nullable = false)
    private Integer width = 960;

    @NotNull
    @Column(nullable = false)
    private Integer leftbarWidth = 180;

    @NotNull
    @Column(nullable = false)
    private Integer rightbarWidth = 180;

    @NotNull
    private String theme;

    @NotNull
    @Column(name = "top_bar" , nullable = false)
    private boolean topBar = true;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLeftbarWidth() {
        return leftbarWidth;
    }

    public void setLeftbarWidth(Integer leftbarWidth) {
        this.leftbarWidth = leftbarWidth;
    }

    public Integer getRightbarWidth() {
        return rightbarWidth;
    }

    public void setRightbarWidth(Integer rightbarWidth) {
        this.rightbarWidth = rightbarWidth;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isTopBar() {
        return topBar;
    }

    public void setTopBar(boolean topBar) {
        this.topBar = topBar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogDesign)) {
            return false;
        }
        return blogId != null && blogId.equals(((BlogDesign) o).blogId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blogId);
    }

    @Override
    public String toString() {
        return "BlogDesign{" +
            "blogId=" + blogId +
            ", width=" + width +
            ", leftbarWidth=" + leftbarWidth +
            ", rightbarWidth=" + rightbarWidth +
            ", theme='" + theme + '\'' +
            ", topBar=" + topBar +
            "}";
    }
}
