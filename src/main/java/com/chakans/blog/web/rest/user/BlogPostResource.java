package com.chakans.blog.web.rest.user;

import com.chakans.blog.config.constants.BlogEnumsConstants;
import com.chakans.blog.service.BlogPostService;
import com.chakans.blog.web.rest.errors.BlogPostStatusNotFoundException;
import com.chakans.blog.web.rest.user.model.request.BlogPostRequestModel;
import com.chakans.blog.web.rest.user.model.response.BlogPostResponseModel;
import com.chakans.core.config.constants.AuthoritiesConstants;
import com.chakans.core.config.constants.Constants;

import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController("blog-v1-user-blog-post-resource")
@RequestMapping(value = "/apis/blog/v1", produces = Constants.APPLICATION_VND_BLOG_V1_USER_JSON)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER + "\")")
public class BlogPostResource {

    private final Logger log = LoggerFactory.getLogger(BlogPostResource.class);

    private final BlogPostService blogPostService;

    public BlogPostResource(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping("/blogs/{blogId}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPage(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogPostRequestModel bpRequestModel) {
        log.debug("REST request to create My BlogPost : {}, {}", blogId, bpRequestModel);
        BlogEnumsConstants.POST_SATAUS status = Optional.of(bpRequestModel.getStatus()).map(BlogEnumsConstants.POST_SATAUS::getEnum).orElseThrow(BlogPostStatusNotFoundException::new);

        blogPostService.createMyBlogPost(blogId, bpRequestModel.getTitle(), bpRequestModel.getContent(),
                bpRequestModel.getPostName(), bpRequestModel.getOpenedDate(), bpRequestModel.getPermitComment(), status, bpRequestModel.getTags());
    }

    @PutMapping("/blogs/{blogId}/posts")
    public void updateMyBlogPage(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogPostRequestModel bpRequestModel) {
        log.debug("REST request to update My BlogPost : {}, {}", blogId, bpRequestModel);
        BlogEnumsConstants.POST_SATAUS status = Optional.of(bpRequestModel.getStatus()).map(BlogEnumsConstants.POST_SATAUS::getEnum).orElseThrow(BlogPostStatusNotFoundException::new);

        blogPostService.updateMyBlogPost(blogId, bpRequestModel.getId(), bpRequestModel.getTitle(),
                bpRequestModel.getContent(), bpRequestModel.getPostName(), bpRequestModel.getOpenedDate(),
                bpRequestModel.getPermitComment(), status, bpRequestModel.getTags());
    }

    @PatchMapping("/blogs/{blogId}/posts")
    public void patchMyBlogPage(
            @RequestHeader("fields") String fields,
            @PathVariable Long blogId,
            @Valid @RequestBody BlogPostRequestModel bpRequestModel) {
        log.debug("REST request to patch up My BlogPost : {}, {}, {}", fields, blogId, bpRequestModel);
        BlogEnumsConstants.POST_SATAUS status = Optional.ofNullable(bpRequestModel.getStatus()).map(BlogEnumsConstants.POST_SATAUS::getEnum).orElse(null);

        blogPostService.patchMyBlogPost(blogId, bpRequestModel.getId(), bpRequestModel.getTitle(),
                bpRequestModel.getContent(), bpRequestModel.getPostName(), bpRequestModel.getOpenedDate(),
                bpRequestModel.getPermitComment(), status, bpRequestModel.getTags(), fields.split(","));
    }

    @GetMapping("/blogs/{blogId}/posts")
    public ResponseEntity<List<BlogPostResponseModel>> getAllMyBlogPosts(
            @PathVariable Long blogId,
            @RequestParam(value = "status") String statusName,
            @RequestParam(value = "unpaged", defaultValue = "false") boolean unpaged,
            @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder,
            Pageable pageable) {
        log.debug("REST request to get My BlogPosts : {}, {}, {}", blogId, statusName, unpaged);
        BlogEnumsConstants.POST_SATAUS status = Optional.of(statusName).map(BlogEnumsConstants.POST_SATAUS::getEnum).orElseThrow(BlogPostStatusNotFoundException::new);
        if (unpaged) pageable = Pageable.unpaged();

        final Page<BlogPostResponseModel> page = blogPostService.getMyBlogPosts(pageable, blogId, status).map(blogPostDTO -> new BlogPostResponseModel(blogPostDTO, false, false));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/blogs/{blogId}/posts/{postId}")
    public ResponseEntity<BlogPostResponseModel> getMyBlogPost(@PathVariable Long blogId, @PathVariable Long postId) {
        log.debug("REST request to get My BlogPost : {}, {}", blogId, postId);
        return ResponseUtil.wrapOrNotFound(blogPostService.getMyBlogPostByBlogIdAndPostId(blogId, postId).map(blogPostDTO -> new BlogPostResponseModel(blogPostDTO, true, true)));
    }

    @DeleteMapping("/blogs/{blogId}/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyBlogPost(@PathVariable Long blogId, @PathVariable Long postId) {
        log.debug("REST request to delete My BlogPost : {}, {}", blogId, postId);
        blogPostService.deleteMyBlogPost(blogId, postId);
    }
}
