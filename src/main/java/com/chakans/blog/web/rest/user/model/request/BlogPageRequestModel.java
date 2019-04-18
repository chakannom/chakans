package com.chakans.blog.web.rest.user.model.request;

import javax.validation.constraints.Size;

public class BlogPageRequestModel {

    private Long id;

    @Size(min = 1, max = 255)
    private String title;
    
    private String content;

    private Boolean permitComment;
    
    private String status;
    
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getPermitComment() {
        return permitComment;
    }

    public void setPermitComment(Boolean permitComment) {
        this.permitComment = permitComment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BlogPageRequestModel{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", permitComment=" + permitComment +
            ", status='" + status + '\'' +
            '}';
    }
}
