package com.chakans.portal.web.rest.anonymous.vm;

import java.time.Instant;

import com.chakans.blog.domain.Blog;
import com.chakans.blog.service.dto.BlogDTO;

/**
 * View Model object for storing a blog.
 */
public class BlogVM {

    private Long id;

    private String title;

    private String description;

    private String url;

    private String status;

    private Instant modified;

    private String link;

    private PostsVM posts;

    private PagesVM pages;

    public BlogVM() {
        // Empty public constructor used by Jackson.
    }

    public BlogVM(BlogDTO blogDTO) {
        this.id = blogDTO.getBlog().getId();
        this.title = blogDTO.getBlog().getTitle();
        this.description = blogDTO.getBlog().getDescription();
        this.url = blogDTO.getBlog().getCustomUrl() == null ? blogDTO.getBlog().getUrl() : blogDTO.getBlog().getCustomUrl();
        this.status = blogDTO.getBlog().getStatus().getName();
        this.modified = blogDTO.getBlog().getModifiedDate();
        this.link = "/apis/blog/v1/blogs/" + id;
        this.posts = new PostsVM();
        this.posts.setCount(blogDTO.getBlog().getPostCount());
        this.posts.setLink(this.link + "/posts");
        this.pages = new PagesVM();
        this.pages.setCount(blogDTO.getBlog().getPageCount());
        this.pages.setLink(this.link + "/pages");
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public PostsVM getPosts() {
        return posts;
    }

    public void setPosts(PostsVM posts) {
        this.posts = posts;
    }

    public PagesVM getPages() {
        return pages;
    }

    public void setPages(PagesVM pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "BlogVM{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", url='" + url + "'" +
            ", status='" + status + "'" +
            ", modified=" + modified +
            ", link='" + link + "'" +
            ", posts=" + posts +
            ", pages=" + pages +
            "}";
    }

    private class PostsVM {

        private Long count;

        private String link;

        public PostsVM() {
            // Empty public constructor used by Jackson.
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Override
        public String toString() {
            return "PostsVM{" +
                "count=" +count +
                ", link='" + link + "'" +
                "}";
        }
    }

    private class PagesVM {

        private Long count;

        private String link;

        public PagesVM() {
            // Empty public constructor used by Jackson.
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Override
        public String toString() {
            return "PagesVM{" +
                "count=" + count +
                ", link='" + link + "'" +
                "}";
        }
    }
}
