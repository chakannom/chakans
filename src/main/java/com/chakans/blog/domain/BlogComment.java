package com.chakans.blog.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.chakans.blog.config.constants.BlogEnumsConstants.COMMENT_OBJECT_TYPE;
import com.chakans.blog.config.constants.BlogEnumsConstants.COMMENT_SATAUS;
import com.chakans.portal.domain.AbstractAuditingEntity;

/**
 * A blog_comment.
 */
@Entity
@Table(name = "blog_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogComment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "blog_id", nullable = false, updatable = false)
    private Long blogId;
    
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "object_type", nullable = false, updatable = false)
    private COMMENT_OBJECT_TYPE objectType;
    
    @NotNull
    @Column(name = "object_id", nullable = false, updatable = false)
    private Long objectId;
    
    @NotNull
    @Column(nullable = false, updatable = false)
    private Integer level;
    
    @Column(name = "parent_id", updatable = false)
    private Long parentId;
    
    @NotNull
    @Size(min = 1, max = 4096)
    @Column(length = 4096, nullable = false)
    private String content;

    @Column(name = "author_name")
    private String authorName;
    
    @Column(name = "author_email")
    private String authorEmail;
    
    @Column(name = "author_url")
    private String authorUrl;
    
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private COMMENT_SATAUS status = COMMENT_SATAUS.PARENTTRASHED;

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

    public COMMENT_OBJECT_TYPE getObjectType() {
        return objectType;
    }

    public void setObjectType(COMMENT_OBJECT_TYPE objectType) {
        this.objectType = objectType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public COMMENT_SATAUS getStatus() {
        return status;
    }

    public void setStatus(COMMENT_SATAUS status) {
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

        BlogComment blogComment = (BlogComment) o;
        return Objects.equals(id, blogComment.getId());
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
            ", objectType=" + objectType +
            ", objectId=" + objectId +
            ", level=" + level +
            ", parentId=" + parentId +
            ", content='" + content + '\'' +
            ", authorName='" + authorName + '\'' +
            ", authorEmail='" + authorEmail + '\'' +
            ", authorUrl='" + authorUrl + '\'' +
            ", status=" + status +
            '}';
    }
}
