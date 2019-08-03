package com.chakans.blog.web.rest.user.model.response;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import com.chakans.blog.domain.BlogPostTag;
import com.chakans.blog.service.dto.BlogPostDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogPostResponseModel {

    private final Long id;
    
    private final String title;
    
    private final String content;

    private final String postName;
    
    private final Instant openedDate;
    
    private final Long viewedCount;
    
    private final Long commentCount;
    
    private final boolean permitComment;
    
    private final String status;
    
    private final Set<String> tags;

    private final Instant createdDate;
    
    private final Instant modifiedDate;

    public BlogPostResponseModel(BlogPostDTO blogPostDTO, boolean showContent, boolean showStatus) {
        this.id = blogPostDTO.getBlogPost().getId();
        this.title = blogPostDTO.getBlogPost().getTitle();
        this.content = showContent ? blogPostDTO.getBlogPost().getContent() : null;
        this.postName = blogPostDTO.getBlogPost().getPostName();
        this.openedDate = blogPostDTO.getBlogPost().getOpenedDate();
        this.viewedCount = blogPostDTO.getBlogPost().getViewedCount();
        this.commentCount = blogPostDTO.getBlogPost().getCommentCount();
        this.permitComment = blogPostDTO.getBlogPost().isPermitComment();
        this.status = showStatus ? blogPostDTO.getBlogPost().getStatus().getName() : null;
        this.tags = blogPostDTO.getBlogPostTags().stream().map(BlogPostTag::getName).collect(Collectors.toSet());
        this.createdDate = blogPostDTO.getBlogPost().getCreatedDate();
        this.modifiedDate = blogPostDTO.getBlogPost().getModifiedDate();
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

    public String getPostName() {
        return postName;
    }

    public Instant getOpenedDate() {
        return openedDate;
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

    public Set<String> getTags() {
        return tags;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }
}
