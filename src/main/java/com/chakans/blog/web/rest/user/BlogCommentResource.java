package com.chakans.blog.web.rest.user;

import java.util.List;
import java.util.Optional;

import com.chakans.blog.config.constants.BlogEnumsConstants;
import com.chakans.blog.web.rest.user.model.request.BlogCommentRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.chakans.blog.service.BlogCommentService;
import com.chakans.blog.web.rest.errors.BlogCommentStatusNotFoundException;
import com.chakans.blog.web.rest.user.model.response.BlogCommentResponseModel;
import com.chakans.core.config.constants.AuthoritiesConstants;
import com.chakans.core.config.constants.Constants;
import com.chakans.core.util.PaginationUtil;

import javax.validation.Valid;

@RestController("blog-v1-user-blog-comment-resource")
@RequestMapping(value = "/apis/blog/v1", produces = Constants.APPLICATION_VND_BLOG_V1_USER_JSON)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER + "\")")
public class BlogCommentResource {

    private final Logger log = LoggerFactory.getLogger(BlogCommentResource.class);
    
    private final BlogCommentService blogCommentService;
    
    public BlogCommentResource(BlogCommentService blogCommentService) {
        this.blogCommentService = blogCommentService;
    }

    @PatchMapping("/blogs/{blogId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public void patchMyBlogPage(
        @RequestHeader("fields") String fields,
        @PathVariable Long blogId,
        @Valid @RequestBody BlogCommentRequestModel bcRequestModel) {

        log.debug("REST request to patch up My BlogComment : {}, {}, {}", fields, blogId, bcRequestModel);
        BlogEnumsConstants.COMMENT_SATAUS status = Optional.ofNullable(bcRequestModel.getStatus()).map(BlogEnumsConstants.COMMENT_SATAUS::getEnum).orElse(null);

        blogCommentService.patchMyBlogComment(blogId, bcRequestModel.getId(), status, fields.split(","));
    }

    @GetMapping("/blogs/{blogId}/comments")
    public ResponseEntity<List<BlogCommentResponseModel>> getAllMyBlogComments(
            @PathVariable Long blogId,
            @RequestParam(value = "status") String statusName, 
            @RequestParam(value = "unpaged", defaultValue = "false") boolean unpaged,
            Pageable pageable) {
        
        log.debug("REST request to get My BlogComments : {}, {}, {}", blogId, statusName, unpaged);
        BlogEnumsConstants.COMMENT_SATAUS status = Optional.ofNullable(statusName).map(BlogEnumsConstants.COMMENT_SATAUS::getEnum).orElseThrow(BlogCommentStatusNotFoundException::new);
        if (unpaged) pageable = Pageable.unpaged();
        
        final Page<BlogCommentResponseModel> page = blogCommentService.getMyBlogComments(pageable, blogId, status).map(BlogCommentResponseModel::new);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/apis/blog/v1/blogs/" + blogId + "/comments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @DeleteMapping("/blogs/{blogId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMyBlogComment(@PathVariable Long blogId, @PathVariable Long commentId) {
        log.debug("REST request to delete My BlogComment : {}, {}", blogId, commentId);
        blogCommentService.deleteMyBlogComment(blogId, commentId);
    }
}
