package com.chakans.blog.web.rest.user;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.chakans.blog.config.constants.BlogEnumsConstants;
import com.chakans.blog.service.BlogPageService;
import com.chakans.blog.web.rest.errors.BlogPageStatusNotFoundException;
import com.chakans.blog.web.rest.user.model.request.BlogPageRequestModel;
import com.chakans.blog.web.rest.user.model.response.BlogPageResponseModel;
import com.chakans.core.config.constants.AuthoritiesConstants;
import com.chakans.core.config.constants.Constants;
import com.chakans.core.util.PaginationUtil;

@RestController("blog-v1-user-blog-page-resource")
@RequestMapping(value = "/apis/blog/v1", produces = Constants.APPLICATION_VND_BLOG_V1_USER_JSON)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER + "\")")
public class BlogPageResource {
    
    private final Logger log = LoggerFactory.getLogger(BlogPageResource.class);
    
    private final BlogPageService blogPageService;
    
    public BlogPageResource(BlogPageService blogPageService) {
        this.blogPageService = blogPageService;
    }

    @PostMapping("/blogs/{blogId}/pages")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPage(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogPageRequestModel bpRequestModel) {
        log.debug("REST request to create My BlogPage : {}, {}", blogId, bpRequestModel);
        BlogEnumsConstants.PAGE_SATAUS status = Optional.of(bpRequestModel.getStatus()).map(BlogEnumsConstants.PAGE_SATAUS::getEnum).orElseThrow(BlogPageStatusNotFoundException::new);

        blogPageService.createMyBlogPage(blogId, bpRequestModel.getTitle(), bpRequestModel.getContent(), bpRequestModel.getPermitComment(), status);
    }
    
    @PutMapping("/blogs/{blogId}/pages")
    @ResponseStatus(HttpStatus.OK)
    public void updateMyBlogPage(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogPageRequestModel bpRequestModel) {
        log.debug("REST request to update My BlogPage : {}, {}", blogId, bpRequestModel);
        BlogEnumsConstants.PAGE_SATAUS status = Optional.of(bpRequestModel.getStatus()).map(BlogEnumsConstants.PAGE_SATAUS::getEnum).orElseThrow(BlogPageStatusNotFoundException::new);

        blogPageService.updateMyBlogPage(blogId, bpRequestModel.getId(), bpRequestModel.getTitle(),
            bpRequestModel.getContent(), bpRequestModel.getPermitComment(), status);
    }
    
    @PatchMapping("/blogs/{blogId}/pages")
    @ResponseStatus(HttpStatus.OK)
    public void patchMyBlogPage(
            @RequestHeader("fields") String fields,
            @PathVariable Long blogId,
            @Valid @RequestBody BlogPageRequestModel bpRequestModel) {
        log.debug("REST request to patch up My BlogPage : {}, {}, {}", fields, blogId, bpRequestModel);
        BlogEnumsConstants.PAGE_SATAUS status = Optional.ofNullable(bpRequestModel.getStatus()).map(BlogEnumsConstants.PAGE_SATAUS::getEnum).orElse(null);

        blogPageService.patchMyBlogPage(blogId, bpRequestModel.getId(), bpRequestModel.getTitle(),
            bpRequestModel.getContent(), bpRequestModel.getPermitComment(), status, fields.split(","));
    }

    @GetMapping("/blogs/{blogId}/pages")
    public ResponseEntity<List<BlogPageResponseModel>> getAllMyBlogPages(
            @PathVariable Long blogId,
            @RequestParam(value = "status") String statusName, 
            @RequestParam(value = "unpaged", defaultValue = "false") boolean unpaged,
            Pageable pageable) {
        log.debug("REST request to get My BlogPages : {}, {}, {}", blogId, statusName, unpaged);
        BlogEnumsConstants.PAGE_SATAUS status = Optional.of(statusName).map(BlogEnumsConstants.PAGE_SATAUS::getEnum).orElseThrow(BlogPageStatusNotFoundException::new);
        if (unpaged) pageable = Pageable.unpaged();

        final Page<BlogPageResponseModel> page = blogPageService.getMyBlogPages(pageable, blogId, status).map(blogPageDTO -> new BlogPageResponseModel(blogPageDTO, false, false));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/apis/blog/v1/blogs/" + blogId + "/pages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/blogs/{blogId}/pages/{pageId}")
    public ResponseEntity<BlogPageResponseModel> getMyBlogPage(@PathVariable Long blogId, @PathVariable Long pageId) {
        log.debug("REST request to get My BlogPage : {}, {}", blogId, pageId);
        return ResponseUtil.wrapOrNotFound(blogPageService.getMyBlogPageByBlogIdAndPageId(blogId, pageId).map(blogPageDTO -> new BlogPageResponseModel(blogPageDTO, true, true)));
    }
    
    @DeleteMapping("/blogs/{blogId}/pages/{pageId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMyBlogPage(@PathVariable Long blogId, @PathVariable Long pageId) {
        log.debug("REST request to delete My BlogPage : {}, {}", blogId, pageId);
        blogPageService.deleteMyBlogPage(blogId, pageId);
    }
}
