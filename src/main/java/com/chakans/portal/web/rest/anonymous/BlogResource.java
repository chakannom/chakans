package com.chakans.portal.web.rest.anonymous;

import com.chakans.blog.domain.Blog;
import com.chakans.blog.service.BlogService;
import com.chakans.blog.service.dto.BlogDTO;
import com.chakans.core.web.rest.errors.BlogAddressNotFoundException;
import com.chakans.portal.web.rest.anonymous.vm.BlogVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("blog-v1-anonymous-blog-resource")
@RequestMapping(value = "/apis/blog/v1", produces = "application/vnd.blog.v1.anonymous+json")
public class BlogResource {

    private final Logger log = LoggerFactory.getLogger(BlogResource.class);

    private final BlogService blogService;

    public BlogResource(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping(value = "/blogs/blog", produces = "text/html;charset=UTF-8")
    public ResponseEntity<String> blog(@RequestHeader(value = "host") String host) {
        Optional<BlogDTO> existingBlog = blogService.getBlogByUrl(host);
        if (!existingBlog.isPresent()) {
            throw new BlogAddressNotFoundException();
        }
        return ResponseUtil.wrapOrNotFound(existingBlog.map(blogDTO -> blogDTO.getBlogDesign().getTheme()));
    }




    /**
     * Retrieving a blog
     *
     * @param blogId
     * @return
     */
    @GetMapping(value = "/blogs/{blogId:\\d+}")
    public ResponseEntity<BlogVM> getBlogVMByBlogId(@PathVariable Long blogId) {
        log.debug("REST request to get Blog by BlogId : {}", blogId);

        return ResponseUtil.wrapOrNotFound(blogService.getBlogById(blogId).map(BlogVM::new));
    }

    /**
     * Retrieving a blog by url
     *
     * @param blogUrl
     * @return
     */
    @GetMapping(value = "/blogs/byurl", params = {"url"})
    public ResponseEntity<BlogVM> getBlogVMByUrl(@RequestParam(value = "url") String blogUrl) {
        log.debug("REST request to get Blog by BlogUrl : {}", blogUrl);

        return ResponseUtil.wrapOrNotFound(blogService.getBlogByUrl(blogUrl).map(BlogVM::new));
    }

    @GetMapping(value = "/blogs/blog/home", produces = "text/html;charset=UTF-8")
    public ResponseEntity<String> home(@RequestHeader(value = "host") String url) {
        log.debug("Request to get a home by url : {}", url);
        Optional<BlogDTO> existingBlog = blogService.getBlogByUrl("dev.chakans.blog");

        Optional<String> home = existingBlog.map(blogDTO -> {
            StringBuilder page = new StringBuilder(blogDTO.getBlogDesign().getTheme());
            int pos = -1;
            //// TOP BAR
            if (blogDTO.getBlogDesign().isTopBar()) {
                pos = page.indexOf("<body");
                pos = page.indexOf(">", pos);
                page.insert(pos + 1, "<iframe frameborder=\"0\" marginheight=\"0\" marginwidth=\"0\" hspace=\"0\" vspace=\"0\" width=\"100%\" scrolling=\"no\" style=\"height:30px\" src=\"/blogs/navbar.c\"></iframe>\n");
            }


            return page.toString();
        });

        return ResponseUtil.wrapOrNotFound(home);
    }
}
