package com.chakans.blog.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.blog.config.constants.BlogEnumsConstants.POST_SATAUS;
import com.chakans.blog.domain.BlogPost;

/**
 * Spring Data JPA repository for the BlogPost entity.
 */
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    Page<BlogPost> findAllByBlogIdAndStatusAndDeletedDateIsNull(Pageable pageable, Long blogId, POST_SATAUS status);

    Optional<BlogPost> findOneByBlogIdAndIdAndDeletedDateIsNull(Long blogId, Long postId);

    List<BlogPost> findAllByDeletedByIsNotNullAndDeletedDateBefore(Instant dateTime);
}
