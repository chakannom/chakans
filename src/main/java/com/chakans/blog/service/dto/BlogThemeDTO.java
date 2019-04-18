package com.chakans.blog.service.dto;

import com.chakans.blog.domain.BlogTheme;
import com.chakans.blog.domain.BlogThemeDescription;

public class BlogThemeDTO {

    private final BlogTheme blogTheme;

    private final BlogThemeDescription blogThemeDescription;

    public BlogThemeDTO(BlogTheme blogTheme, BlogThemeDescription blogThemeDescription) {
        this.blogTheme = blogTheme;
        this.blogThemeDescription = blogThemeDescription;
    }

    public BlogTheme getBlogTheme() {
        return blogTheme;
    }

    public BlogThemeDescription getBlogThemeDescription() {
        return blogThemeDescription;
    }
}
