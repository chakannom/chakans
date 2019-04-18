package com.chakans.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.blog.domain.BlogDesign;

public interface BlogDesignRepository extends JpaRepository<BlogDesign, Long> {
}
