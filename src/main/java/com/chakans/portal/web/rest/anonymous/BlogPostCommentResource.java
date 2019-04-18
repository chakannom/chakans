package com.chakans.portal.web.rest.anonymous;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("blog-v1-anonymous-blog-post-comment-resource")
@RequestMapping(value = "/apis/blog/v1", produces = "application/vnd.blog.v1.anonymous+json")
public class BlogPostCommentResource {

    private final Logger log = LoggerFactory.getLogger(BlogPostCommentResource.class);

    public BlogPostCommentResource() {
    }

    // Retrieving comments for a post (/blogs/{blogId}/posts/{postId}/comments)
    // Retrieving a specific comment (/blogs/{blogId}/posts/{postId}/comments/{commentId})
}
