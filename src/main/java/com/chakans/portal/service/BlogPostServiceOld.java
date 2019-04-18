package com.chakans.portal.service;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chakans.portal.repository.BlogPostRepositoryOld;

/**
 * Service class for managing blogPosts.
 */
@Service
@Transactional
public class BlogPostServiceOld {

    private final Logger log = LoggerFactory.getLogger(BlogPostServiceOld.class);

    private final BlogPostRepositoryOld blogPostRepository;
      
    public BlogPostServiceOld(BlogPostRepositoryOld blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }       
/*
    @Transactional(readOnly = true)
    public Page<BlogPostDTO> getBlogPostDTOsByBlogId(Long blogId, Pageable pageable) {
        return blogPostRepository.findByBlogId(blogId, pageable).map(BlogPostDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<BlogPostDTO> getBlogPostDTOByBlogIdAndPostId(Long blogId, Long postId) {
        return blogPostRepository.findOneByBlogIdAndPostId(blogId, postId).map(BlogPostDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<BlogPostDTO> getBlogPostDTOsByBlogIdAndSearch(Long blogId, String query, Pageable pageable) {
        return blogPostRepository.findByBlogIdAndSearch(blogId, query, pageable).map(BlogPostDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<BlogPostDTO> getBlogPostDTOByBlogIdAndPostNameAndOpenedDate(Long blogId, String postName, Integer year, Integer month) {
        return blogPostRepository.findOneByBlogIdAndPostNameAndOpenedDate(blogId, postName, year, month).map(BlogPostDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<BlogPostDTO> getBlogPostDTOsByBlogIdAndCreatedDateBetween(Long blogId, Instant startDate, Instant endDate, Pageable pageable) {
        return blogPostRepository.findByBlogIdAndCreatedDateBetween(blogId, startDate, endDate, pageable).map(BlogPostDTO::new);
    }
*/
}
