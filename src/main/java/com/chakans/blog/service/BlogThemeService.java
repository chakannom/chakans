package com.chakans.blog.service;

import com.chakans.blog.config.constants.BlogEnumsConstants;
import com.chakans.blog.domain.BlogThemeDescription;
import com.chakans.blog.repository.BlogThemeDescriptionRepository;
import com.chakans.blog.service.dto.BlogThemeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chakans.blog.repository.BlogThemeRepository;

import java.util.Optional;

/**
 * Service class for managing blogThemes.
 */
@Service
@Transactional
public class BlogThemeService {

    private final Logger log = LoggerFactory.getLogger(BlogThemeService.class);

    private final BlogThemeRepository blogThemeRepository;

    private final BlogThemeDescriptionRepository blogThemeDescriptionRepository;

    public BlogThemeService(BlogThemeRepository blogThemeRepository, BlogThemeDescriptionRepository blogThemeDescriptionRepository) {
        this.blogThemeRepository = blogThemeRepository;
        this.blogThemeDescriptionRepository = blogThemeDescriptionRepository;
    }

    @Transactional(readOnly = true)
    public Page<BlogThemeDTO> getBlogThemes(Pageable pageable, String langKey) {
        return blogThemeRepository.findAllByStatusAndDeletedDateIsNull(pageable, BlogEnumsConstants.THEME_SATAUS.PUBLISHED)
            .map(blogTheme -> {
                BlogThemeDescription blogThemeDescription = blogThemeDescriptionRepository.findOneByThemeIdAndLangKey(blogTheme.getId(), langKey).get();
                return new BlogThemeDTO(blogTheme, blogThemeDescription);
            });

    }

    @Transactional(readOnly = true)
    public Optional<BlogThemeDTO> getBlogThemeByThemeIdAndLangKey(Long themeId, String langKey) {
        return blogThemeRepository.findOneByIdAndStatusAndDeletedDateIsNull(themeId, BlogEnumsConstants.THEME_SATAUS.PUBLISHED)
            .map(blogTheme -> {
                BlogThemeDescription blogThemeDescription = blogThemeDescriptionRepository.findOneByThemeIdAndLangKey(blogTheme.getId(), langKey).get();
                return new BlogThemeDTO(blogTheme, blogThemeDescription);
            });
    }

    @Transactional(readOnly = true)
    public Optional<BlogThemeDTO> getBlogThemeByThemeId(Long themeId) {
        return blogThemeRepository.findOneByIdAndStatusAndDeletedDateIsNull(themeId, BlogEnumsConstants.THEME_SATAUS.PUBLISHED)
            .map(blogTheme -> new BlogThemeDTO(blogTheme, null));
    }
}
