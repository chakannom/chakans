package com.chakans.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chakans.blog.config.constants.BlogEnumsConstants;
import com.chakans.blog.domain.BlogPage;
import com.chakans.blog.repository.BlogPageRepository;
import com.chakans.blog.repository.BlogUserRepository;
import com.chakans.blog.service.dto.BlogPageDTO;
import com.chakans.core.security.SecurityUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

/**
 * Service class for managing blogPages.
 */
@Service
@Transactional
public class BlogPageService {

    private final Logger log = LoggerFactory.getLogger(BlogPageService.class);

    private final BlogPageRepository blogPageRepository;

    private final BlogUserRepository blogUserRepository;

    public BlogPageService(BlogPageRepository blogPageRepository, BlogUserRepository blogUserRepository) {
        this.blogPageRepository = blogPageRepository;
        this.blogUserRepository = blogUserRepository;
    }

    public Optional<BlogPageDTO> createMyBlogPage(Long blogId, String title, String content, boolean permitComment, BlogEnumsConstants.PAGE_SATAUS status) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> {
                BlogPage newBlogPage = new BlogPage();
                newBlogPage.setBlogId(blogId);
                newBlogPage.setTitle(title);
                newBlogPage.setContent(content);
                newBlogPage.setPermitComment(permitComment);
                newBlogPage.setStatus(status);
                blogPageRepository.save(newBlogPage);
                log.debug("Created Information for My BlogPage: {}", newBlogPage);

                return new BlogPageDTO(newBlogPage);
            });
    }

    public Optional<BlogPageDTO> updateMyBlogPage(Long blogId, Long pageId, String title, String content, boolean permitComment, BlogEnumsConstants.PAGE_SATAUS status) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> {
                BlogPage blogPage = blogPageRepository.findOneByBlogIdAndIdAndDeletedDateIsNull(blogId, pageId).get();
                blogPage.setTitle(title);
                blogPage.setContent(content);
                blogPage.setPermitComment(permitComment);
                blogPage.setStatus(status);
                log.debug("Changed Information for My BlogPage: {}", blogPage);

                return new BlogPageDTO(blogPage);
            });
    }

    public Optional<BlogPageDTO> patchMyBlogPage(Long blogId, Long pageId, String title, String content, Boolean permitComment, BlogEnumsConstants.PAGE_SATAUS status, String[] fields) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> {
                BlogPage blogPage = blogPageRepository.findOneByBlogIdAndIdAndDeletedDateIsNull(blogId, pageId).get();
                if (Arrays.stream(fields).anyMatch("title"::equalsIgnoreCase)) {
                    blogPage.setTitle(title);
                }
                if (Arrays.stream(fields).anyMatch("content"::equalsIgnoreCase)) {
                    blogPage.setContent(content);
                }
                if (Arrays.stream(fields).anyMatch("permitComment"::equalsIgnoreCase)) {
                    blogPage.setPermitComment(permitComment);
                }
                if (Arrays.stream(fields).anyMatch("status"::equalsIgnoreCase)) {
                    blogPage.setStatus(status);
                }

                return new BlogPageDTO(blogPage);
            });
    }

    public void deleteMyBlogPage(Long blogId, Long pageId) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .ifPresent(blogUser ->
                blogPageRepository.findOneByBlogIdAndIdAndDeletedDateIsNull(blogId, pageId)
                    .ifPresent(blogPage -> {
                        blogPage.setDeletedBy(blogUser.getUserLogin());
                        blogPage.setDeletedDate(Instant.now());
                        log.debug("Deleted My BlogPage: {}", blogPage);
                    })
            );
    }

    @Transactional(readOnly = true)
    public Page<BlogPageDTO> getMyBlogPages(Pageable pageable, Long blogId, BlogEnumsConstants.PAGE_SATAUS status) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> blogPageRepository.findAllByBlogIdAndStatusAndDeletedDateIsNull(pageable, blogUser.getBlogId(), status))
            .get().map(BlogPageDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<BlogPageDTO> getMyBlogPageByBlogIdAndPageId(Long blogId, Long pageId) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> blogPageRepository.findOneByBlogIdAndIdAndDeletedDateIsNull(blogId, pageId))
            .get().map(BlogPageDTO::new);
    }

    /**
     * Not activated blogPages should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeDeletedBlogPage() {
        blogPageRepository.findAllByDeletedByIsNotNullAndDeletedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(blogPage -> {
                log.debug("Removing deleted blogPage {}", blogPage.getId());
                blogPageRepository.delete(blogPage);
            });
    }
}
