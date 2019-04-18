package com.chakans.portal.web.rest.anonymous;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("blog-v1-anonymous-blog-page-resource")
@RequestMapping(value = "/apis/blog/v1", produces = "application/vnd.blog.v1.anonymous+json")
public class BlogPageResource {

    private final Logger log = LoggerFactory.getLogger(BlogPageResource.class);

    public BlogPageResource() {
    }

    // Retrieving pages for a blog (/blogs/{blogId}/pages)
    // Retrieving a specific page (/blogs/{blogId}/pages/{pageId})
}
