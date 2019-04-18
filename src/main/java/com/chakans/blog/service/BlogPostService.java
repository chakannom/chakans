package com.chakans.blog.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chakans.blog.config.constants.BlogEnumsConstants;
import com.chakans.blog.domain.BlogPost;
import com.chakans.blog.domain.BlogPostTag;
import com.chakans.blog.repository.BlogPostRepository;
import com.chakans.blog.repository.BlogPostTagRepository;
import com.chakans.blog.repository.BlogUserRepository;
import com.chakans.blog.service.dto.BlogPostDTO;
import com.chakans.core.security.SecurityUtils;

/**
 * Service class for managing blogPosts.
 */
@Service
@Transactional
public class BlogPostService {
    
    private final Logger log = LoggerFactory.getLogger(BlogPostService.class);
    
    private final BlogPostRepository blogPostRepository;
    
    private final BlogPostTagRepository blogPostTagRepository;

    private final BlogUserRepository blogUserRepository;
    
    public BlogPostService(BlogPostRepository blogPostRepository, BlogPostTagRepository blogPostTagRepository, BlogUserRepository blogUserRepository) {
        this.blogPostRepository = blogPostRepository;
        this.blogPostTagRepository = blogPostTagRepository;
        this.blogUserRepository = blogUserRepository;
    }
    
    public Optional<BlogPostDTO> createMyBlogPost(Long blogId, String title, String content, String postName, Instant openedDate, boolean permitComment, BlogEnumsConstants.POST_SATAUS status, Set<String> tags) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> {
                BlogPost newBlogPost = new BlogPost();
                newBlogPost.setBlogId(blogId);
                newBlogPost.setTitle(title);
                newBlogPost.setContent(content);
                newBlogPost.setPostName(postName);
                newBlogPost.setOpenedDate(openedDate);
                newBlogPost.setPermitComment(permitComment);
                newBlogPost.setStatus(status);
                blogPostRepository.save(newBlogPost);
                log.debug("Created Information for My BlogPost: {}", newBlogPost);
                
                List<BlogPostTag> newBlogPostTags = tags.stream().map(tagName -> {
                    BlogPostTag newBlogPostTag = new BlogPostTag();
                    newBlogPostTag.setPostId(newBlogPost.getId());
                    newBlogPostTag.setBlogId(blogId);
                    newBlogPostTag.setName(tagName);
                    return blogPostTagRepository.save(newBlogPostTag);
                }).collect(Collectors.toList());
                log.debug("Created Information for My BlogPostTags: {}", newBlogPostTags);
                
                return new BlogPostDTO(newBlogPost, newBlogPostTags);
            });
    }
    
    public Optional<BlogPostDTO> updateMyBlogPost(Long blogId, Long postId, String title, String content, String postName, Instant openedDate, boolean permitComment, BlogEnumsConstants.POST_SATAUS status, Set<String> tags) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> {
                BlogPost blogPost = blogPostRepository.findOneByBlogIdAndIdAndDeletedDateIsNull(blogId, postId).get();
                blogPost.setTitle(title);
                blogPost.setContent(content);
                blogPost.setPostName(postName);
                blogPost.setOpenedDate(openedDate);
                blogPost.setPermitComment(permitComment);
                blogPost.setStatus(status);
                log.debug("Changed Information for My BlogPost: {}", blogPost);

                List<BlogPostTag> blogPostTags = blogPostTagRepository.findAllByBlogIdAndPostId(blogId, postId);
                blogPostTags.clear();
                tags.stream().map(tagName -> {
                    BlogPostTag blogPostTag = new BlogPostTag();
                    blogPostTag.setPostId(blogPost.getId());
                    blogPostTag.setBlogId(blogId);
                    blogPostTag.setName(tagName);
                    return blogPostTag;
                }).forEach(blogPostTags::add);
                log.debug("Changed Information for My BlogPostTags: {}", blogPostTags);

                return new BlogPostDTO(blogPost, blogPostTags);
            });
    }

    public Optional<BlogPostDTO> patchMyBlogPost(Long blogId, Long postId, String title, String content, String postName, Instant openedDate, Boolean permitComment, BlogEnumsConstants.POST_SATAUS status, Set<String> tags, String[] fields) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> {
                BlogPost blogPost = blogPostRepository.findOneByBlogIdAndIdAndDeletedDateIsNull(blogId, postId).get();
                List<BlogPostTag> blogPostTags = blogPostTagRepository.findAllByBlogIdAndPostId(blogId, postId);

                if (Arrays.stream(fields).anyMatch("title"::equalsIgnoreCase)) {
                    blogPost.setTitle(title);
                }
                if (Arrays.stream(fields).anyMatch("content"::equalsIgnoreCase)) {
                    blogPost.setContent(content);
                }
                if (Arrays.stream(fields).anyMatch("postName"::equalsIgnoreCase)) {
                    blogPost.setPostName(postName);
                }
                if (Arrays.stream(fields).anyMatch("openedDate"::equalsIgnoreCase)) {
                    blogPost.setOpenedDate(openedDate);
                }
                if (Arrays.stream(fields).anyMatch("permitComment"::equalsIgnoreCase)) {
                    blogPost.setPermitComment(permitComment);
                }
                if (Arrays.stream(fields).anyMatch("status"::equalsIgnoreCase)) {
                    blogPost.setStatus(status);
                }
                if (Arrays.stream(fields).anyMatch("tags"::equalsIgnoreCase)) {
                    blogPostTags.clear();
                    tags.stream().map(tagName -> {
                        BlogPostTag blogPostTag = new BlogPostTag();
                        blogPostTag.setPostId(blogPost.getId());
                        blogPostTag.setBlogId(blogId);
                        blogPostTag.setName(tagName);
                        return blogPostTag;
                    }).forEach(blogPostTags::add);
                }

                return new BlogPostDTO(blogPost, blogPostTags);
            });
    }
    
    public void deleteMyBlogPost(Long blogId, Long postId) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .ifPresent(blogUser ->
                blogPostRepository.findOneByBlogIdAndIdAndDeletedDateIsNull(blogId, postId)
                    .ifPresent(blogPost -> {
                        blogPost.setDeletedBy(blogUser.getUserLogin());
                        blogPost.setDeletedDate(Instant.now());
                        log.debug("Deleted My BlogPost: {}", blogPost);

                        blogPostTagRepository.deleteAllByBlogIdAndPostId(blogId, postId);
                        log.debug("Deleted My BlogPostTags");
                    })
            );
    }
    
    @Transactional(readOnly = true)
    public Page<BlogPostDTO> getMyBlogPosts(Pageable pageable, Long blogId, BlogEnumsConstants.POST_SATAUS status) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> blogPostRepository.findAllByBlogIdAndStatusAndDeletedDateIsNull(pageable, blogUser.getBlogId(), status))
            .get().map(blogPost -> new BlogPostDTO(blogPost, blogPostTagRepository.findAllByBlogIdAndPostId(blogId, blogPost.getId())));
    }

    @Transactional(readOnly = true)
    public Optional<BlogPostDTO> getMyBlogPostByBlogIdAndPostId(Long blogId, Long postId) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> blogPostRepository.findOneByBlogIdAndIdAndDeletedDateIsNull(blogId, postId))
            .get().map(blogPost -> new BlogPostDTO(blogPost, blogPostTagRepository.findAllByBlogIdAndPostId(blogId, blogPost.getId())));
    }

    /**
     * Not activated blogPosts should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeDeletedBlogPost() {
        blogPostRepository.findAllByDeletedByIsNotNullAndDeletedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(blogPost -> {
                log.debug("Removing deleted blogPost {}", blogPost.getId());
                blogPostRepository.delete(blogPost);
            });
    }

}
