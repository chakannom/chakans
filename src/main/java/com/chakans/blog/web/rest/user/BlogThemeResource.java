package com.chakans.blog.web.rest.user;

import com.chakans.blog.web.rest.user.model.response.BlogThemeResponseModel;
import com.chakans.core.config.constants.AuthoritiesConstants;
import com.chakans.core.config.constants.Constants;
import com.chakans.blog.service.BlogThemeService;

import io.github.jhipster.web.util.PaginationUtil;
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

import java.util.List;

@RestController("blog-v1-user-blog-theme-resource")
@RequestMapping(value = "/apis/blog/v1", produces = Constants.APPLICATION_VND_BLOG_V1_USER_JSON)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER + "\")")
public class BlogThemeResource {

    private final Logger log = LoggerFactory.getLogger(BlogThemeResource.class);

    private final BlogThemeService blogThemeService;

    public BlogThemeResource(BlogThemeService blogThemeService) {
        this.blogThemeService = blogThemeService;
    }

    @GetMapping("/themes")
    public ResponseEntity<List<BlogThemeResponseModel>> getBlogThemes(
            @RequestHeader("lang-key") String langKey,
            @RequestParam(value = "unpaged", defaultValue = "false") boolean unpaged,
            @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder,
            Pageable pageable) {
        log.debug("REST request to get BlogThemes : {}, {}", langKey, unpaged);
        if (unpaged) pageable = Pageable.unpaged();

        final Page<BlogThemeResponseModel> page = blogThemeService.getBlogThemes(pageable, langKey).map(blogThemeDTO -> new BlogThemeResponseModel(blogThemeDTO, true));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
