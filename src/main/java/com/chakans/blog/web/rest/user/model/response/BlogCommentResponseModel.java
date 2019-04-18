package com.chakans.blog.web.rest.user.model.response;

import java.time.Instant;

import com.chakans.blog.domain.BlogComment;
import com.chakans.blog.domain.BlogPage;
import com.chakans.blog.domain.BlogPost;
import com.chakans.blog.service.dto.BlogCommentDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogCommentResponseModel {
    
    private final Long id;

    private final String content;

    private final ParentComment parent;

    private final Page page;

    private final Post post;

    private final Author author;

    private final Instant createdDate;
    
    private final Instant modifiedDate;

    public BlogCommentResponseModel(BlogCommentDTO blogCommentDTO) {
        this.id = blogCommentDTO.getBlogComment().getId();
        this.content = blogCommentDTO.getBlogComment().getContent();
        BlogComment parentComment = blogCommentDTO.getParentBlogComment();
        this.parent = parentComment != null ? new ParentComment(parentComment) : null;
        Object blogObject = blogCommentDTO.getBlogObject();
        if (blogObject instanceof BlogPage) {
            this.page = new Page((BlogPage)blogObject);
            this.post = null;
        } else {
            this.page = null;
            this.post = new Post((BlogPost)blogObject);
        }
        String authorName = blogCommentDTO.getBlogComment().getAuthorName();
        String authorEmail = blogCommentDTO.getBlogComment().getAuthorEmail();
        String authorUrl = blogCommentDTO.getBlogComment().getAuthorUrl();
        this.author = new Author(authorName, authorEmail, authorUrl);
        this.createdDate = blogCommentDTO.getBlogComment().getCreatedDate();
        this.modifiedDate = blogCommentDTO.getBlogComment().getModifiedDate();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public ParentComment getParent() {
        return parent;
    }

    public Page getPage() {
        return page;
    }

    public Post getPost() {
        return post;
    }

    public Author getAuthor() {
        return author;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public class ParentComment {

        private final Long id;

        private final String content;

        public ParentComment(BlogComment blogComment) {
            this.id = blogComment.getId();
            this.content = blogComment.getContent();
        }

        public Long getId() {
            return id;
        }

        public String getContent() {
            return content;
        }
    }

    public class Page {

        private final Long id;

        private final String title;

        public Page(BlogPage blogPage) {
            this.id = blogPage.getId();
            this.title = blogPage.getTitle();
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

    public class Post {

        private final Long id;

        private final String title;

        public Post(BlogPost blogPost) {
            this.id = blogPost.getId();
            this.title = blogPost.getTitle();
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

    public class Author {
        
        private final String name;
        
        private final String email;
        
        private final String url;
        
        public Author(String name, String email, String url) {
            this.name = name;
            this.email = email;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getUrl() {
            return url;
        }
    }
}
