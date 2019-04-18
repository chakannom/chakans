package com.chakans.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.portal.domain.Language;

/**
 * Spring Data JPA repository for the Language entity.
 */
public interface LanguageRepository extends JpaRepository<Language, String> {
}
