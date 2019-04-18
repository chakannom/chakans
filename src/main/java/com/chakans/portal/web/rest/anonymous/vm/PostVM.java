package com.chakans.portal.web.rest.anonymous.vm;

/**
 * View Model object for storing a post.
 */
public class PostVM {

    private String title;

    private String content;

    public PostVM() {
        // Empty public constructor used by Jackson.
    }

    public PostVM(Object postDTO) {
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

    @Override
    public String toString() {
        return "PostVM{" +
            "title='" + title + "'" +
            ", content='" + content + "'" +
            "}";
    }
}
