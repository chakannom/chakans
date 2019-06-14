package com.chakans.drive.repository;

import com.chakans.drive.domain.DrivePersonalNode;
import com.chakans.drive.domain.id.DrivePersonalNodeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link DrivePersonalNode} entity.
 */
public interface DrivePersonalNodeRepository extends JpaRepository<DrivePersonalNode, DrivePersonalNodeId> {

    Optional<DrivePersonalNode> findOneByIdAndUserLogin(String id, String userLogin);
}
