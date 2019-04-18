package com.chakans.blog.web.rest.user.model.request;

import java.time.Instant;
import java.util.Set;

import javax.validation.constraints.Size;

public class BlogPostRequestModel {

    private Long id;

    @Size(min = 1, max = 255)
    private String title;
    
    private String content;
    
    private String postName;
    
    private Instant openedDate;

    private Boolean permitComment;
    
    private String status;
    
    private Set<String> tags;
    
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

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Instant getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(Instant openedDate) {
        this.openedDate = openedDate;
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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "BlogPageRequestModel{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", postName='" +postName + '\'' +
            ", openedDate=" + openedDate +
            ", permitComment=" + permitComment +
            ", status='" + status + '\'' +
            ", tags=" + tags +
            '}';
    }
}
