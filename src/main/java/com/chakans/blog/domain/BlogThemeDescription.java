package com.chakans.blog.domain;

import com.chakans.blog.domain.id.BlogThemeDescriptionId;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * A blog_theme_template.
 */
@Entity
@IdClass(BlogThemeDescriptionId.class)
@Table(name = "cks_blog_theme_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogThemeDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "theme_id", updatable = false)
    private Long themeId;

    @Id
    @Column(name = "lang_key", updatable = false)
    private String langKey;

    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    private String description;

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogThemeDescription)) return false;

        BlogThemeDescription blogThemeDescription = (BlogThemeDescription) o;
        return Objects.equals(themeId, blogThemeDescription.getThemeId())
            && Objects.equals(langKey, blogThemeDescription.getLangKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(themeId, langKey);
    }

    @Override
    public String toString() {
        return "BlogThemeDescription{" +
            "themeId=" + themeId +
            ", langKey='" + langKey + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            "}";
    }

}
