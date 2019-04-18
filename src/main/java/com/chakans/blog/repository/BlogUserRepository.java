package com.chakans.blog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.blog.domain.BlogUser;
import com.chakans.blog.domain.id.BlogUserId;

public interface BlogUserRepository extends JpaRepository<BlogUser, BlogUserId> {
    
    Optional<BlogUser> findOneByBlogIdAndUserLogin(Long blogId, String userLogin);
    
    Page<BlogUser> findAllByUserLogin(Pageable pageable, String userLogin);
}
