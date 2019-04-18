package com.chakans.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.blog.config.constants.BlogEnumsConstants.PAGE_SATAUS;
import com.chakans.blog.domain.BlogPage;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the BlogPage entity.
 */
public interface BlogPageRepository extends JpaRepository<BlogPage, Long> {

    Page<BlogPage> findAllByBlogIdAndStatusAndDeletedDateIsNull(Pageable pageable, Long blogId, PAGE_SATAUS status);

    Optional<BlogPage> findOneByBlogIdAndIdAndDeletedDateIsNull(Long blogId, Long pageId);
    
    List<BlogPage> findAllByDeletedByIsNotNullAndDeletedDateBefore(Instant dateTime);
}
