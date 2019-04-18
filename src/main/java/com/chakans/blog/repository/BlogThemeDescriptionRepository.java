package com.chakans.blog.repository;

import com.chakans.blog.domain.BlogThemeDescription;
import com.chakans.blog.domain.id.BlogThemeDescriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the BlogThemeDescription entity.
 */
public interface BlogThemeDescriptionRepository extends JpaRepository<BlogThemeDescription, BlogThemeDescriptionId> {

    Optional<BlogThemeDescription> findOneByThemeIdAndLangKey(Long themeId, String langKey);
}
