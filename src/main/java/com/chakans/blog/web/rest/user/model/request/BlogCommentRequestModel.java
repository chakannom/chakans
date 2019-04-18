package com.chakans.blog.web.rest.user.model.request;

import javax.validation.constraints.Size;

public class BlogCommentRequestModel {

    private Long id;

    @Size(min = 1, max = 4096)
    private String content;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BlogCommentRequestModel{" +
            "id=" + id +
            ", status=" + status +
            '}';
    }
}
