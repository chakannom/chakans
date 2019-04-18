package com.chakans.blog.repository;

import com.chakans.blog.domain.BlogThemeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the BlogThemeTemplate entity.
 */
public interface BlogThemeTemplateRepository extends JpaRepository<BlogThemeTemplate, Long> {
}
