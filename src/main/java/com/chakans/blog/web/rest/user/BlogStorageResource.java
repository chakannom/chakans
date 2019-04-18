package com.chakans.blog.web.rest.user;

import com.chakans.core.config.constants.AuthoritiesConstants;
import com.chakans.blog.service.BlogStorageService;
import com.chakans.core.config.constants.Constants;
import com.google.common.collect.ImmutableMap;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController("blog-v1-user-blog-storage-resource")
@RequestMapping(value = "/apis/blog/v1", produces = Constants.APPLICATION_VND_BLOG_V1_USER_JSON)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER + "\")")
public class BlogStorageResource {

    private final Logger log = LoggerFactory.getLogger(BlogStorageResource.class);

    private final BlogStorageService blogStorageService;

    public BlogStorageResource(BlogStorageService blogStorageService) {
        this.blogStorageService = blogStorageService;
    }

    @GetMapping("/storages/default/presigned-put-url")
    public ResponseEntity<Map<String, String>> getDefaultPresignedPutUrl(@RequestParam("filename") String filename) {
        return ResponseUtil.wrapOrNotFound(blogStorageService.getPresignedPutUrl(null, filename).map(url -> ImmutableMap.of("url", url)));
    }

    @GetMapping("/storages/blogs/{blogId}/presigned-put-url")
    public ResponseEntity<Map<String, String>> getBlogPresignedPutUrl(@PathVariable Long blogId, @RequestParam("filename") String filename) {
        return ResponseUtil.wrapOrNotFound(blogStorageService.getPresignedPutUrl(blogId, filename).map(url -> ImmutableMap.of("url", url)));
    }

    @GetMapping("/storages/blogs/{blogId}")
    public ResponseEntity<List<Map<String, String>>> getBlogImageList(@PathVariable Long blogId) {
        List<Map<String, String>> imageList = blogStorageService.getImageList(blogId).stream()
            .map(url -> ImmutableMap.of("url", url)).collect(Collectors.toList());
        return new ResponseEntity<>(imageList, HttpStatus.OK);
    }
}
