package com.chakans.blog.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chakans.blog.config.constants.BlogEnumsConstants.THEME_SATAUS;
import com.chakans.portal.domain.AbstractAuditingEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A theme.
 */
@Entity
@Table(name = "blog_theme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogTheme extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 512)
    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private THEME_SATAUS status = THEME_SATAUS.DRAFT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public THEME_SATAUS getStatus() {
        return status;
    }

    public void setStatus(THEME_SATAUS status) {
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

        BlogTheme blogTheme = (BlogTheme) o;
        return Objects.equals(id, blogTheme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BlogTheme{" +
            "id=" + id +
            ", imageUrl='" + imageUrl + '\'' +
            ", status=" + status +
            '}';
    }
}
