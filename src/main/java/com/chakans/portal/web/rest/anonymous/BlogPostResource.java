package com.chakans.portal.web.rest.anonymous;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chakans.core.util.PaginationUtil;
import com.chakans.portal.service.BlogPostServiceOld;
import com.chakans.portal.web.rest.anonymous.vm.BlogPostVM;

import io.github.jhipster.web.util.ResponseUtil;

@RestController("blog-v1-anonymous-blog-post-resource")
@RequestMapping(value = "/apis/blog/v1", produces = "application/vnd.blog.v1.anonymous+json")
public class BlogPostResource {

    private final Logger log = LoggerFactory.getLogger(BlogPostResource.class);

    private final BlogPostServiceOld blogPostService;

    public BlogPostResource(BlogPostServiceOld blogPostService) {
        this.blogPostService = blogPostService;
    }

    /**
     * Retrieving posts from a blog
     *
     * @param blogId
     * @param pageable
     * @return
     */
    /*
    @GetMapping(value = "/blogs/{blogId:\\d+}/posts")
    @Timed
    public ResponseEntity<List<BlogPostVM>> getBlogPostVMsByBlogId(@PathVariable Long blogId, Pageable pageable) {
        log.debug("REST request to get BlogPostVMs by blogId: {}", blogId);

        final Page<BlogPostDTO> page = blogPostService.getBlogPostDTOsByBlogId(blogId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/apis/blog/v1/blogs/" + blogId + "/posts");
        return new ResponseEntity<>(page.map(BlogPostVM::new).getContent(), headers, HttpStatus.OK);
    }
    */
    
    /**
     * Retrieving a specific post
     *
     * @param blogId
     * @param postId
     * @return
     */
    /*
    @GetMapping(value = "/blogs/{blogId:\\d+}/posts/{postId:\\d+}")
    @Timed
    public ResponseEntity<BlogPostVM> getBlogPostVMByBlogIdAndPostId(@PathVariable Long blogId, @PathVariable Long postId) {
        log.debug("REST request to get BlogPostVM by blogId & postId : {}, {}", blogId, postId);

        return ResponseUtil.wrapOrNotFound(blogPostService.getBlogPostDTOByBlogIdAndPostId(blogId, postId).map(BlogPostVM::new));
    }
    */

    /**
     * Searching for a post
     *
     * @param blogId
     * @param query
     * @param pageable
     * @return
     */
    /*
    @GetMapping(value = "/blogs/{blogId:\\d+}/posts/search", params = {"q"})
    @Timed
    public ResponseEntity<List<BlogPostVM>> getBlogPostVMsByBlogIdAndSearch(@PathVariable Long blogId, @RequestParam(value = "q") String query, Pageable pageable) {
        log.debug("REST request to get BlogPostVMs by blogId & query : {}, {}", blogId, query);

        final Page<BlogPostDTO> page = blogPostService.getBlogPostDTOsByBlogIdAndSearch(blogId, query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/apis/blog/v1/blogs/" + blogId + "/posts/search?q=" + query);
        return new ResponseEntity<>(page.map(BlogPostVM::new).getContent(), headers, HttpStatus.OK);
    }
    */

    /**
     * Retrieving a post by its path(/YEAR/MONTH/POSTNAME.CKS)
     *
     * @param blogId
     * @param path
     * @return
     */
    /*
    @GetMapping(value = "/blogs/{blogId:\\d+}/posts/bypath", params = {"path"})
    @Timed
    public ResponseEntity<BlogPostVM> getBlogPostVMByBlogIdAndPath(@PathVariable Long blogId, @RequestParam(value = "path") String path) {
        log.debug("REST request to get BlogPostVM by blogId & path : {}, {}", blogId, path);

        String[] splitedPath = path.split("/");
        Integer year = Integer.valueOf(splitedPath[1]);
        Integer month = Integer.valueOf(splitedPath[2]);
        String postName = splitedPath[3];
        return ResponseUtil.wrapOrNotFound(blogPostService.getBlogPostDTOByBlogIdAndPostNameAndOpenedDate(blogId, postName, year, month).map(BlogPostVM::new));
    }
    */
}
