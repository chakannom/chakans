package com.chakans.blog.web.rest.user;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chakans.blog.service.BlogPostTagService;
import com.chakans.blog.web.rest.user.model.response.BlogPostTagResponseModel;
import com.chakans.core.config.constants.AuthoritiesConstants;
import com.chakans.core.config.constants.Constants;

import io.github.jhipster.web.util.PaginationUtil;

@RestController("blog-v1-user-blog-post-tag-resource")
@RequestMapping(value = "/apis/blog/v1", produces = Constants.APPLICATION_VND_BLOG_V1_USER_JSON)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER + "\")")
public class BlogPostTagResource {
    
    private final Logger log = LoggerFactory.getLogger(BlogPostTagResource.class);
    
    private final BlogPostTagService blogPostTagService;
    
    public BlogPostTagResource(BlogPostTagService blogPostTagService) {
        this.blogPostTagService = blogPostTagService;
    }

    @GetMapping("/blogs/{blogId}/posts/tags")
    public List<BlogPostTagResponseModel> getAllMyBlogPostTags(
            @PathVariable Long blogId,
            @RequestParam(value = "showPostCount", defaultValue = "false") boolean showPostCount) {
        log.debug("REST request to get My BlogPostTags : {}, {}", blogId, showPostCount);

        Map<String, List<Long>> mapGroupingBy = blogPostTagService.getMyBlogPostTags(blogId).stream()
            .collect(Collectors.groupingBy(bptDTO -> bptDTO.getBlogPostTag().getName(),
                Collectors.mapping(bptDTO -> bptDTO.getBlogPostTag().getPostId(), Collectors.toList())));

        List<BlogPostTagResponseModel> list = mapGroupingBy.entrySet().stream()
            .map(bptEntry -> new BlogPostTagResponseModel(bptEntry.getKey(), bptEntry.getValue(), showPostCount))
            .sorted(Comparator.comparing(BlogPostTagResponseModel::getName))
            .collect(Collectors.toList());

        return list;
    }
}
