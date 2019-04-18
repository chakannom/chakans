package com.chakans.blog.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.chakans.core.config.constants.Constants;
import com.chakans.blog.config.constants.BlogEnumsConstants.BLOG_SATAUS;
import com.chakans.portal.domain.AbstractAuditingEntity;

/**
 * A blog.
 */
@Entity
@Table(name = "blog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Blog extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String description;

    @NotNull
    @Pattern(regexp = Constants.BLOG_URL_REGEX)
    @Size(min = 15, max = 115)
    @Column(length = 115, nullable = false)
    private String url;

    @Pattern(regexp = Constants.BLOG_CUSTOM_URL_REGEX)
    @Size(min = 4, max = 253)
    @Column(name = "custom_url",length = 253, nullable = false)
    private String customUrl;

    @NotNull
    @Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 6)
    private String langKey;

    @NotNull
    @Column(name = "post_count", nullable = false)
    private Long postCount = (long) 0;

    @NotNull
    @Column(name = "page_count", nullable = false)
    private Long pageCount = (long) 0;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private BLOG_SATAUS status = BLOG_SATAUS.OPENED;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Long getPostCount() {
        return postCount;
    }

    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public BLOG_SATAUS getStatus() {
        return status;
    }

    public void setStatus(BLOG_SATAUS status) {
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

        Blog blog = (Blog) o;
        return Objects.equals(id, blog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Blog{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", url='" + url + '\'' +
            ", customUrl='" + customUrl + '\'' +
            ", langKey='" + langKey + '\'' +
            ", postCount=" + postCount +
            ", pageCount=" + pageCount +
            ", status=" + status +
            '}';
    }
}
