package com.chakans.blog.web.rest.user.model.response;

import com.chakans.blog.service.dto.BlogThemeDTO;

public class BlogThemeResponseModel {

    private final Long id;

    private final String imageUrl;

    private final String name;

    private final String description;

    public BlogThemeResponseModel(BlogThemeDTO blogThemeDTO, boolean showDescription) {
        this.id = blogThemeDTO.getBlogTheme().getId();
        this.imageUrl = blogThemeDTO.getBlogTheme().getImageUrl();
        this.name = blogThemeDTO.getBlogThemeDescription().getName();
        this.description = showDescription ? blogThemeDTO.getBlogThemeDescription().getDescription() : null;
    }

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
