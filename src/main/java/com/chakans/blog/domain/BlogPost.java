package com.chakans.blog.domain;

import java.io.Serializable;
import java.time.Instant;
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

import com.chakans.blog.config.constants.BlogEnumsConstants.POST_SATAUS;
import com.chakans.portal.domain.AbstractAuditingEntity;

/**
 * A blog_post.
 */
@Entity
@Table(name = "cks_blog_post")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogPost extends AbstractAuditingEntity implements Serializable {

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
    @Column(name = "post_name", length = 255, nullable = false)
    private String postName;

    @NotNull
    @Column(name = "opened_date")
    private Instant openedDate = Instant.now();

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
    private POST_SATAUS status = POST_SATAUS.DRAFT;

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

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Instant getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(Instant openedDate) {
        this.openedDate = openedDate;
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

    public POST_SATAUS getStatus() {
        return status;
    }

    public void setStatus(POST_SATAUS status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogPost)) {
            return false;
        }
        return id != null && id.equals(((BlogPost) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BlogPost{" +
            "id=" + id +
            ", blogId=" + blogId +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", postName='" + postName + '\'' +
            ", openedDate=" + openedDate +
            ", viewedCount=" + viewedCount +
            ", commentCount=" + commentCount +
            ", permitComment=" + permitComment +
            ", status=" + status +
            "}";
    }

}
