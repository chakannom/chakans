package com.chakans.blog.domain.id;

import java.io.Serializable;
import java.util.Objects;

/**
 * An id for blog_theme_description.
 */
public class BlogThemeDescriptionId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long themeId;

    private String langKey;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogThemeDescriptionId blogThemeDescriptionId = (BlogThemeDescriptionId) o;
        return Objects.equals(themeId, blogThemeDescriptionId.getThemeId())
            && Objects.equals(langKey, blogThemeDescriptionId.getLangKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(themeId, langKey);
    }
}
