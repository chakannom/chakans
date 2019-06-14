package com.chakans.drive.repository;

import com.chakans.drive.domain.DrivePersonalTree;
import com.chakans.drive.domain.id.DrivePersonalTreeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link DrivePersonalTree} entity.
 */
public interface DrivePersonalTreeRepository extends JpaRepository<DrivePersonalTree, DrivePersonalTreeId> {

    Optional<DrivePersonalTree> findOneByIdAndUserLogin(String id, String userLogin);
}
