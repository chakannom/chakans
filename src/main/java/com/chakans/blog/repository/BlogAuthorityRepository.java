package com.chakans.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.blog.domain.BlogAuthority;

public interface BlogAuthorityRepository extends JpaRepository<BlogAuthority, String> {
}
