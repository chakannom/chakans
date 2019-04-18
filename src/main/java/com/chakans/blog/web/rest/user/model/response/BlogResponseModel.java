package com.chakans.blog.web.rest.user.model.response;

import java.time.Instant;

import com.chakans.blog.domain.BlogDesign;
import com.chakans.blog.service.dto.BlogDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogResponseModel {
    
    private final Long id;
    
    private final String title;
    
    private final String description;
    
    private final String url;
    
    private final String customUrl;
    
    private final String langKey;
    
    private final String status;
    
    private final Design design;
    
    private final Instant createdDate;
    
    private final Instant modifiedDate;

    public BlogResponseModel(BlogDTO blogDTO) {
        this.id = blogDTO.getBlog().getId();
        this.title = blogDTO.getBlog().getTitle();
        this.description = blogDTO.getBlog().getDescription();
        this.url = blogDTO.getBlog().getUrl();
        this.customUrl = blogDTO.getBlog().getCustomUrl();
        this.langKey = blogDTO.getBlog().getLangKey();
        this.status = blogDTO.getBlog().getStatus().getName();
        this.design = new Design(blogDTO.getBlogDesign());
        this.createdDate = blogDTO.getBlog().getCreatedDate();
        this.modifiedDate = blogDTO.getBlog().getModifiedDate();
    }
    
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getStatus() {
        return status;
    }

    public Design getDesign() {
        return design;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public class Design {
        
        private final Integer width;

        private final Integer leftbarWidth;

        private final Integer rightbarWidth;

        private final String theme;

        private final boolean topBar;

        public Design(BlogDesign blogDesign) {
            this.width = blogDesign.getWidth();
            this.leftbarWidth = blogDesign.getLeftbarWidth();
            this.rightbarWidth = blogDesign.getRightbarWidth();
            this.theme = blogDesign.getTheme();
            this.topBar = blogDesign.isTopBar();
        }

        public Integer getWidth() {
            return width;
        }

        public Integer getLeftbarWidth() {
            return leftbarWidth;
        }

        public Integer getRightbarWidth() {
            return rightbarWidth;
        }

        public String getTheme() {
            return theme;
        }

        public Boolean isTopBar() {
            return topBar;
        }
    }
}
