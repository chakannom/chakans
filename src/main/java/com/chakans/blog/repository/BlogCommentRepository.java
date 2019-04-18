package com.chakans.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.blog.config.constants.BlogEnumsConstants.COMMENT_SATAUS;
import com.chakans.blog.domain.BlogComment;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the BlogComment entity.
 */
public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {

    Page<BlogComment> findAllByBlogIdAndStatusAndDeletedDateIsNull(Pageable pageable, Long blogId, COMMENT_SATAUS status);

    Optional<BlogComment> findOneByBlogIdAndIdAndDeletedDateIsNull(Long blogId, Long commentId);

    List<BlogComment> findAllByDeletedByIsNotNullAndDeletedDateBefore(Instant dateTime);
}
