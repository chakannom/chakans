package com.chakans.blog.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.chakans.blog.config.constants.BlogEnumsConstants.PAGE_SATAUS;
import com.chakans.portal.domain.AbstractAuditingEntity;

/**
 * A blog_page.
 */
@Entity
@Table(name = "blog_page")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogPage extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "blog_id", nullable = false, updatable = false)
    private Long blogId;
    
    @NotNull
    @Size(min = 1, max = 255)
    @Column(length = 255, nullable = false)
    private String title;

    @Lob
    private String content;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "page_name", length = 255, nullable = false)
    private String pageName;

    @NotNull
    @Column(name = "viewed_count", nullable = false)
    private Long viewedCount = (long) 0;
    
    @NotNull
    @Column(name = "comment_count", nullable = false)
    private Long commentCount = (long) 0;
    
    @NotNull
    @Column(name = "permit_comment", nullable = false)
    private boolean permitComment = false;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private PAGE_SATAUS status = PAGE_SATAUS.DRAFT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Long getViewedCount() {
        return viewedCount;
    }

    public void setViewedCount(Long viewedCount) {
        this.viewedCount = viewedCount;
    }
    
    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isPermitComment() {
        return permitComment;
    }

    public void setPermitComment(boolean permitComment) {
        this.permitComment = permitComment;
    }

    public PAGE_SATAUS getStatus() {
        return status;
    }

    public void setStatus(PAGE_SATAUS status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlogPage blogPage = (BlogPage) o;
        return Objects.equals(id, blogPage.getId());
    }    

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BlogPage{" +
            "id=" + id +
            ", blogId=" + blogId +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", pageName='" + pageName + '\'' +
            ", viewedCount=" + viewedCount +
            ", commentCount=" + commentCount +
            ", permitComment=" + permitComment +
            ", status=" + status +
            '}';
    }
}
