package com.chakans.portal.web.rest.anonymous.vm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class BlogPostVM {

    private Long id;

    private Long blogId;

    private String title;

    private String content;

    private Instant opened;

    private Instant modified;

    private Long view;  

    private String url;

    private String link;

    private AuthorVM author;

    private CommentsVM comments;

    private FilesVM files;

    public BlogPostVM() {
        // Empty public constructor used by Jackson.
    }

    public BlogPostVM(Object blogPostDTO) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
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

    public Instant getOpened() {
        return opened;
    }

    public void setOpened(Instant opened) {
        this.opened = opened;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AuthorVM getAuthor() {
        return author;
    }

    public void setAuthor(AuthorVM author) {
        this.author = author;
    }

    public CommentsVM getComments() {
        return comments;
    }

    public void setComments(CommentsVM comments) {
        this.comments = comments;
    }

    public FilesVM getFiles() {
        return files;
    }

    public void setFiles(FilesVM files) {
        this.files = files;
    }

    private class AuthorVM {

        private String nickname;

        private String email;

        public AuthorVM() {
            // Empty public constructor used by Jackson.
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "AuthorVM{" +
                "nickname='" + nickname + "'" +
                ", email='" + email + "'" +
                "}";
        }
    }

    private class CommentsVM {

        private Long count;

        private String link;

        public CommentsVM() {
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
            return "CommentsVM{" +
                "count=" + count +
                ", link='" + link + "'" +
                "}";
        }
    }

    private class FilesVM {

        private Long count;

        private String link;

        public FilesVM() {
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
            return "FilesVM{" +
                "count=" + count +
                ", link='" + link + "'" +
                "}";
        }
    }
}
