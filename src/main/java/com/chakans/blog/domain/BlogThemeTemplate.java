package com.chakans.blog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A blog_theme_template.
 */
@Entity
@Table(name = "cks_blog_theme_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogThemeTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "theme_id", updatable = false)
    private Long themeId;

    @NotNull
    private String content;

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogThemeTemplate)) {
            return false;
        }
        return themeId != null && themeId.equals(((BlogThemeTemplate) o).themeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(themeId);
    }

    @Override
    public String toString() {
        return "BlogThemeTemplate{" +
            "themeId=" + themeId +
            ", content='" + content + '\'' +
            "}";
    }
}
