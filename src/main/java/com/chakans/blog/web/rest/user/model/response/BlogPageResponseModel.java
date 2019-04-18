package com.chakans.blog.web.rest.user.model.response;

import java.time.Instant;

import com.chakans.blog.config.constants.BlogConstants;
import com.chakans.blog.service.dto.BlogPageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogPageResponseModel {

    private final Long id;
    
    private final String title;
    
    private final String content;
    
    private final Long viewedCount;
    
    private final Long commentCount;
    
    private final boolean permitComment;
    
    private final String status;

    private final Instant createdDate;
    
    private final Instant modifiedDate;

    public BlogPageResponseModel(BlogPageDTO blogPageDTO, boolean showContent, boolean showStatus) {
        this.id = blogPageDTO.getBlogPage().getId();
        this.title = blogPageDTO.getBlogPage().getTitle();
        this.content = showContent ? blogPageDTO.getBlogPage().getContent() : null;
        this.viewedCount = blogPageDTO.getBlogPage().getViewedCount();
        this.commentCount = blogPageDTO.getBlogPage().getCommentCount();
        this.permitComment = blogPageDTO.getBlogPage().isPermitComment();
        this.status = showStatus ? blogPageDTO.getBlogPage().getStatus().getName() : null;
        this.createdDate = blogPageDTO.getBlogPage().getCreatedDate();
        this.modifiedDate = blogPageDTO.getBlogPage().getModifiedDate();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getViewedCount() {
        return viewedCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public boolean isPermitComment() {
        return permitComment;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }
}
