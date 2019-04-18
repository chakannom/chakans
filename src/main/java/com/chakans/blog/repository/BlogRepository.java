package com.chakans.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.blog.domain.Blog;

/**
 * Spring Data JPA repository for the Blog entity.
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findOneByUrl(String url);
}
