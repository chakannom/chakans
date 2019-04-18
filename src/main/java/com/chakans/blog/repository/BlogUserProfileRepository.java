package com.chakans.blog.repository;

import java.util.Optional;

import com.chakans.blog.domain.BlogUserProfile;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the BlogUserProfile entity.
 */
public interface BlogUserProfileRepository extends JpaRepository<BlogUserProfile, String> {

    Optional<BlogUserProfile> findOneByUserLogin(String userLogin);
}
