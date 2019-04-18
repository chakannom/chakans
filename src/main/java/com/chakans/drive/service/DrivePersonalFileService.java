package com.chakans.drive.service;

import com.chakans.core.security.SecurityUtils;
import com.chakans.core.util.RandomUtil;
import com.chakans.drive.domain.DrivePersonalNode;
import com.chakans.drive.domain.DrivePersonalTree;
import com.chakans.drive.repository.DrivePersonalNodeRepository;
import com.chakans.drive.repository.DrivePersonalTreeRepository;
import com.chakans.drive.service.dto.DrivePersonalFileDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for managing drive_personal_node and drive_personal_tree.
 */
@Service
@Transactional
public class DrivePersonalFileService {

    private static final String DEFAULT_PERMISSION = "700";

    private final Logger log = LoggerFactory.getLogger(DrivePersonalFileService.class);

    private final DrivePersonalNodeRepository drivePersonalNodeRepository;

    private final DrivePersonalTreeRepository drivePersonalTreeRepository;

    public DrivePersonalFileService(DrivePersonalNodeRepository drivePersonalNodeRepository, DrivePersonalTreeRepository drivePersonalTreeRepository) {
        this.drivePersonalNodeRepository = drivePersonalNodeRepository;
        this.drivePersonalTreeRepository = drivePersonalTreeRepository;
    }

    public Optional<DrivePersonalFileDTO> createMyDrivePersonalFile(String name, String mimeType, Long size, String parentId) {
        return SecurityUtils.getCurrentUserLogin()
            .map(userLogin -> {
                String id = RandomUtil.getRandomUUIDForUser(drivePersonalNodeRepository, "findOneByIdAndUserLogin", userLogin);

                DrivePersonalNode newDrivePersonalNode = new DrivePersonalNode();
                newDrivePersonalNode.setId(id);
                newDrivePersonalNode.setUserLogin(userLogin);
                newDrivePersonalNode.setName(name);
                newDrivePersonalNode.setMimeType(mimeType);
                newDrivePersonalNode.setSize(size);
                newDrivePersonalNode.setPermission(DEFAULT_PERMISSION);
                drivePersonalNodeRepository.save(newDrivePersonalNode);
                log.debug("Created Information for My DrivePersonalNode: {}", newDrivePersonalNode);

                DrivePersonalTree newDrivePersonalTree = new DrivePersonalTree();
                newDrivePersonalTree.setId(id);
                newDrivePersonalTree.setUserLogin(userLogin);
                Integer parentLevel = drivePersonalTreeRepository.findOneByIdAndUserLogin(parentId, userLogin).map(DrivePersonalTree::getLevel).get();
                newDrivePersonalTree.setLevel(parentLevel + 1);
                newDrivePersonalTree.setParentId(parentId);
                drivePersonalTreeRepository.save(newDrivePersonalTree);
                log.debug("Created Information for My DrivePersonalTree: {}", newDrivePersonalTree);

                return new DrivePersonalFileDTO(newDrivePersonalNode, newDrivePersonalTree);
            });
    }

}
