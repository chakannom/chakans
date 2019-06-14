package com.chakans.blog.web.rest.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.chakans.blog.config.constants.BlogEnumsConstants;
import com.chakans.blog.service.BlogThemeService;
import com.chakans.blog.web.rest.errors.BlogAddressAlreadyUsedException;
import com.chakans.blog.web.rest.errors.BlogStatusNotFoundException;
import com.chakans.blog.web.rest.errors.BlogThemeNotFoundException;
import com.chakans.core.config.constants.Constants;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.chakans.blog.service.BlogService;
import com.chakans.blog.service.dto.BlogDTO;
import com.chakans.blog.web.rest.user.model.request.BlogMakerRequestModel;
import com.chakans.blog.web.rest.user.model.request.BlogRequestModel;
import com.chakans.blog.web.rest.user.model.response.BlogResponseModel;
import com.chakans.core.config.constants.AuthoritiesConstants;

import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController("blog-v1-user-blog-resource")
@RequestMapping(value = "/apis/blog/v1", produces = Constants.APPLICATION_VND_BLOG_V1_USER_JSON)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER + "\")")
public class BlogResource {

    private final Logger log = LoggerFactory.getLogger(BlogResource.class);

    private final BlogService blogService;

    private final BlogThemeService blogThemeService;

    public BlogResource(BlogService blogService, BlogThemeService blogThemeService) {
        this.blogService = blogService;
        this.blogThemeService = blogThemeService;
    }

    @PostMapping("/blogs")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBlog(@Valid @RequestBody BlogMakerRequestModel bmRequestModel) {
        log.debug("REST request to create My Blog : {}", bmRequestModel);
        if (blogService.getBlogBySubdomain(bmRequestModel.getSubdomain()).isPresent()) {
            throw new BlogAddressAlreadyUsedException();
        } else if (!blogThemeService.getBlogThemeByThemeId(bmRequestModel.getThemeId()).isPresent()) {
            throw new BlogThemeNotFoundException();
        }

        blogService.createMyBlog(bmRequestModel.getTitle(), bmRequestModel.getSubdomain(), bmRequestModel.getThemeId());
    }

    @PutMapping("/blogs")
    public void updateMyBlog(@Valid @RequestBody BlogRequestModel bRequestModel) {
        log.debug("REST request to update My Blog : {}", bRequestModel);
        BlogEnumsConstants.BLOG_SATAUS status = Optional.of(bRequestModel.getStatus()).map(BlogEnumsConstants.BLOG_SATAUS::getEnum).orElseThrow(BlogStatusNotFoundException::new);

        blogService.updateMyBlog(bRequestModel.getId(), bRequestModel.getTitle(), bRequestModel.getDescription(), bRequestModel.getUrl(),
                bRequestModel.getCustomUrl(), bRequestModel.getLangKey(), status,
                bRequestModel.getDesign().getWidth(), bRequestModel.getDesign().getLeftbarWidth(),
                bRequestModel.getDesign().getRightbarWidth(), bRequestModel.getDesign().getTheme(), bRequestModel.getDesign().isTopBar());
    }

    @PatchMapping("/blogs")
    public void patchMyBlog(
            @RequestHeader("fields") String fields,
            @Valid @RequestBody BlogRequestModel bRequestModel) {
        log.debug("REST request to patch up My Blog : {}, {}", fields, bRequestModel);
        BlogEnumsConstants.BLOG_SATAUS status = Optional.ofNullable(bRequestModel.getStatus()).map(BlogEnumsConstants.BLOG_SATAUS::getEnum).orElse(null);

        blogService.patchMyBlog(bRequestModel.getId(), bRequestModel.getTitle(), bRequestModel.getDescription(), bRequestModel.getUrl(),
                bRequestModel.getCustomUrl(), bRequestModel.getLangKey(), status,
                bRequestModel.getDesign().getWidth(), bRequestModel.getDesign().getLeftbarWidth(),
                bRequestModel.getDesign().getRightbarWidth(), bRequestModel.getDesign().getTheme(), bRequestModel.getDesign().isTopBar(), fields.split(","));
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogResponseModel>> getAllMyBlogs(
            @RequestParam(value = "unpaged", defaultValue = "false") boolean unpaged,
            @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder,
            Pageable pageable) {
        log.debug("REST request to get My Blogs");
        if (unpaged) pageable = Pageable.unpaged();

        final Page<BlogResponseModel> page = blogService.getMyBlogs(pageable).map(BlogResponseModel::new);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/blogs/{blogId:\\d+}")
    public ResponseEntity<BlogResponseModel> getMyBlog(@PathVariable Long blogId) {
        log.debug("REST request to get Blog : {}", blogId);
        return ResponseUtil.wrapOrNotFound(blogService.getMyBlogById(blogId).map(BlogResponseModel::new));
    }

    @GetMapping("/blogs/check-subdomain-availability")
    public Map<String, Boolean> checkSubdomainAvailability(@RequestParam(value = "subdomain") String subdomain) {
        log.debug("REST request to check subdomain's availability: {}", subdomain);
        return ImmutableMap.of("availability", !blogService.getBlogBySubdomain(subdomain).isPresent());
    }
}
