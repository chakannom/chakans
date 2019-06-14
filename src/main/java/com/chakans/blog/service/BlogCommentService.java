package com.chakans.blog.service;

import com.chakans.blog.config.constants.BlogEnumsConstants;
import com.chakans.blog.domain.BlogComment;
import com.chakans.blog.repository.BlogPageRepository;
import com.chakans.blog.repository.BlogPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chakans.blog.repository.BlogCommentRepository;
import com.chakans.blog.repository.BlogUserRepository;
import com.chakans.blog.service.dto.BlogCommentDTO;
import com.chakans.core.security.SecurityUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

/**
 * Service class for managing blogComments.
 */
@Service
@Transactional
public class BlogCommentService {

    private final Logger log = LoggerFactory.getLogger(BlogCommentService.class);

    private final BlogCommentRepository blogCommentRepository;

    private final BlogUserRepository blogUserRepository;

    private final BlogPageRepository blogPageRepository;

    private final BlogPostRepository blogPostRepository;

    public BlogCommentService(BlogCommentRepository blogCommentRepository, BlogUserRepository blogUserRepository,
                              BlogPageRepository blogPageRepository, BlogPostRepository blogPostRepository) {
        this.blogCommentRepository = blogCommentRepository;
        this.blogUserRepository = blogUserRepository;
        this.blogPageRepository = blogPageRepository;
        this.blogPostRepository = blogPostRepository;
    }

    public Optional<BlogCommentDTO> patchMyBlogComment(Long blogId, Long commentId, BlogEnumsConstants.COMMENT_SATAUS status, String[] fields) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> {
                BlogComment blogComment = blogCommentRepository.findOneByBlogIdAndIdAndDeletedDateIsNull(blogId, commentId).get();
                if (Arrays.stream(fields).anyMatch("status"::equalsIgnoreCase)) {
                    blogComment.setStatus(status);
                }

                return new BlogCommentDTO(blogComment, null, null);
            });
    }


    public void deleteMyBlogComment(Long blogId, Long commentId) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .ifPresent(blogUser ->
                blogCommentRepository.findOneByBlogIdAndIdAndDeletedDateIsNull(blogId, commentId)
                    .ifPresent(blogComment -> {
                        blogComment.setDeletedBy(blogUser.getUserLogin());
                        blogComment.setDeletedDate(Instant.now());
                        log.debug("Deleted My BlogComment: {}", blogComment);
                    })
            );
    }

    @Transactional(readOnly = true)
    public Page<BlogCommentDTO> getMyBlogComments(Pageable pageable, Long blogId, BlogEnumsConstants.COMMENT_SATAUS status) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> blogCommentRepository.findAllByBlogIdAndStatusAndDeletedDateIsNull(pageable, blogUser.getBlogId(), status)).get()
            .map(blogComment -> {
                BlogComment parentBlogComment = null;
                if (blogComment.getParentId() != null) {
                    parentBlogComment = blogCommentRepository.findById(blogComment.getParentId()).orElse(null);
                }
                Object blogObject;
                if (blogComment.getObjectType() == BlogEnumsConstants.COMMENT_OBJECT_TYPE.PAGE) {
                    blogObject = blogPageRepository.findById(blogComment.getObjectId()).get();
                } else {
                    blogObject = blogPostRepository.findById(blogComment.getObjectId()).get();
                }

                return new BlogCommentDTO(blogComment, parentBlogComment, blogObject);
            });
    }

    /**
     * Not activated blogComments should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeDeletedBlogComment() {
        blogCommentRepository.findAllByDeletedByIsNotNullAndDeletedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(blogComment -> {
                log.debug("Removing deleted blogComment {}", blogComment.getId());
                blogCommentRepository.delete(blogComment);
            });
    }

}
