package com.chakans.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.blog.domain.BlogPostTag;
import com.chakans.blog.domain.id.BlogPostTagId;

public interface BlogPostTagRepository extends JpaRepository<BlogPostTag, BlogPostTagId> {
    
    List<BlogPostTag> findAllByBlogIdAndPostId(Long blogId, Long postId);
    
    void deleteAllByBlogIdAndPostId(Long blogId, Long postId);
}