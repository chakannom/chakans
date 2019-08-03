package com.chakans.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chakans.blog.domain.BlogPostTag;
import com.chakans.blog.repository.BlogPostTagRepository;
import com.chakans.blog.repository.BlogUserRepository;
import com.chakans.blog.service.dto.BlogPostTagDTO;
import com.chakans.core.security.SecurityUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing blogPostTags.
 */
@Service
@Transactional
public class BlogPostTagService {
    
    private final Logger log = LoggerFactory.getLogger(BlogPostTagService.class);
    
    private final BlogPostTagRepository blogPostTagRepository;

    private final BlogUserRepository blogUserRepository;
    
    public BlogPostTagService(BlogPostTagRepository blogPostTagRepository, BlogUserRepository blogUserRepository) {
        this.blogPostTagRepository = blogPostTagRepository;
        this.blogUserRepository = blogUserRepository;
    }

    @Transactional(readOnly = true)
    public List<BlogPostTagDTO> getMyBlogPostTags(Long blogId) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> blogPostTagRepository.findAllByBlogId(blogUser.getBlogId()))
            .get().stream().map(BlogPostTagDTO::new).collect(Collectors.toList());
    }

}
