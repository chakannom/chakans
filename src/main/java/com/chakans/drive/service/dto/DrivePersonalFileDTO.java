package com.chakans.drive.service.dto;

import com.chakans.drive.domain.DrivePersonalNode;
import com.chakans.drive.domain.DrivePersonalTree;

public class DrivePersonalFileDTO {

    private final DrivePersonalNode drivePersonalNode;
    
    private final DrivePersonalTree drivePersonalTree;

    public DrivePersonalFileDTO(DrivePersonalNode drivePersonalNode, DrivePersonalTree drivePersonalTree) {
        this.drivePersonalNode = drivePersonalNode;
        this.drivePersonalTree = drivePersonalTree;
    }

    public DrivePersonalNode getDrivePersonalNode() {
        return drivePersonalNode;
    }

    public DrivePersonalTree getDrivePersonalTree() {
        return drivePersonalTree;
    }
}