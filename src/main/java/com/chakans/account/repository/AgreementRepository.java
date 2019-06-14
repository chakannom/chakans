package com.chakans.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chakans.account.domain.Agreement;

/**
 * Spring Data JPA repository for the {@link Agreement} entity.
 */
public interface AgreementRepository extends JpaRepository<Agreement, String> {
    
    Optional<Agreement> findOneByName(String name);
}
