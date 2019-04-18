package com.chakans.blog.repository;

import com.chakans.blog.config.constants.BlogEnumsConstants.THEME_SATAUS;
import com.chakans.blog.domain.BlogTheme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the BlogTheme entity.
 */
public interface BlogThemeRepository extends JpaRepository<BlogTheme, Long> {

    Page<BlogTheme> findAllByStatusAndDeletedDateIsNull(Pageable pageable, THEME_SATAUS status);

    Optional<BlogTheme> findOneByIdAndStatusAndDeletedDateIsNull(Long themeId, THEME_SATAUS status);
}
