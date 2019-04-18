package com.chakans.blog.web.rest.user.model.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.chakans.core.config.constants.Constants;

public class BlogRequestModel {

    private Long id;

    @Size(min = 1, max = 100)
    private String title;

    private String description;

    @Pattern(regexp = Constants.BLOG_URL_REGEX)
    @Size(min = 15, max = 115)
    private String url;

    @Pattern(regexp = Constants.BLOG_CUSTOM_URL_REGEX)
    @Size(min = 4, max = 253)
    private String customUrl;

    @Size(min = 2, max = 6)
    private String langKey;

    private String status;
    
    private Design design = new Design();

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Design getDesign() {
        return design;
    }

    public void setDesign(Design design) {
        this.design = design;
    }

    public class Design {
        
        private Integer width;

        private Integer leftbarWidth;

        private Integer rightbarWidth;

        private String theme;

        private Boolean topBar;

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getLeftbarWidth() {
            return leftbarWidth;
        }

        public void setLeftbarWidth(Integer leftbarWidth) {
            this.leftbarWidth = leftbarWidth;
        }

        public Integer getRightbarWidth() {
            return rightbarWidth;
        }

        public void setRightbarWidth(Integer rightbarWidth) {
            this.rightbarWidth = rightbarWidth;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public Boolean isTopBar() {
            return topBar;
        }

        public void setTopBar(Boolean topBar) {
            this.topBar = topBar;
        }

        @Override
        public String toString() {
            return "Design{" +
                "width=" + width +
                ", leftbarWidth=" + leftbarWidth +
                ", rightbarWidth=" + rightbarWidth +
                ", theme='" + theme + '\'' +
                ", topBar=" + topBar +
                '}';
        }
    }

    @Override
    public String toString() {
        return "BlogRequestModel{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", url='" + url + '\'' +
            ", customUrl='" + customUrl + '\'' +
            ", langKey='" + langKey + '\'' +
            ", status='" + status + '\'' +
            ", design=" + design +
            '}';
    }
    
}
