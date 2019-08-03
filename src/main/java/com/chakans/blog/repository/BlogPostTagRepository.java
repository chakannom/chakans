package com.chakans.blog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chakans.blog.domain.BlogPostTag;
import com.chakans.blog.domain.id.BlogPostTagId;

public interface BlogPostTagRepository extends JpaRepository<BlogPostTag, BlogPostTagId> {
    
    List<BlogPostTag> findAllByBlogIdAndPostId(Long blogId, Long postId);
    
    void deleteAllByBlogIdAndPostId(Long blogId, Long postId);

    List<BlogPostTag> findAllByBlogId(Long blogId);
}
