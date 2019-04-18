package com.chakans.portal.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chakans.blog.domain.BlogPage;
import com.chakans.portal.domain.id.BlogPostId;

/**
 * Spring Data JPA repository for the BlogPost entity.
 */
public interface BlogPostRepositoryOld extends JpaRepository<BlogPage, Long> {
/*
    Page<BlogPost> findByBlogId(Long blogId, Pageable pageable);

    Optional<BlogPost> findOneByBlogIdAndPostId(Long blogId, Long postId);

    @Query(value = "SELECT blog_post FROM BlogPost blog_post INNER JOIN blog_post.post post WHERE blog_post.blogId = :blogId AND (LOWER(post.title) LIKE CONCAT('%', LOWER(:query), '%') OR LOWER(post.content) LIKE CONCAT('%', LOWER(:query), '%'))")
    Page<BlogPost> findByBlogIdAndSearch(@Param("blogId") Long blogId, @Param("query") String query, Pageable pageable);
    
    @Query(value = "SELECT blog_post FROM BlogPost blog_post INNER JOIN blog_post.post post WHERE blog_post.blogId = :blogId AND blog_post.postName = :postName AND YEAR(post.openedDate) = :year AND MONTH(post.openedDate) = :month")
    Optional<BlogPost> findOneByBlogIdAndPostNameAndOpenedDate(@Param("blogId") Long blogId, @Param("postName") String postName, @Param("year") Integer year, @Param("month") Integer month);

    @Query(value = "SELECT blog_post FROM BlogPost blog_post INNER JOIN blog_post.post post WHERE blog_post.blogId = :blogId AND post.createdDate BETWEEN :startDate AND :endDate")
    Page<BlogPost> findByBlogIdAndCreatedDateBetween(@Param("blogId") Long blogId, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate, Pageable pageable);
*/
}
