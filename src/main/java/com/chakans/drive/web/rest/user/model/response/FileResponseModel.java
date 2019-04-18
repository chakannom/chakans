package com.chakans.drive.web.rest.user.model.response;

import com.chakans.drive.service.dto.DrivePersonalFileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResponseModel {

    private final String id;

    private final String name;

    private final String mimeType;

    private final String permission;

    private final Instant createdDate;

    private final Instant modifiedDate;

    private final Parent parent;

    private final Owner owner;

    public FileResponseModel(DrivePersonalFileDTO drivePersonalFileDTO) {
        this.id = drivePersonalFileDTO.getDrivePersonalNode().getId();
        this.name = drivePersonalFileDTO.getDrivePersonalNode().getName();
        this.mimeType = drivePersonalFileDTO.getDrivePersonalNode().getMimeType();
        this.permission = drivePersonalFileDTO.getDrivePersonalNode().getPermission();
        this.createdDate = drivePersonalFileDTO.getDrivePersonalNode().getCreatedDate();
        this.modifiedDate = drivePersonalFileDTO.getDrivePersonalNode().getModifiedDate();
        this.parent = new Parent(drivePersonalFileDTO.getDrivePersonalTree().getParentId());
        this.owner = new Owner(null);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getPermission() {
        return permission;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public Parent getParent() {
        return parent;
    }

    public Owner getOwner() {
        return owner;
    }

    public class Parent {
        
        private final String id;

        public Parent(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public class Owner {
        
        private final String email;

        public Owner(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

}



/*
{
    "id": "user-1",
    "name": "user-1",
    "mimeType": "application/vnd.chakans.folder"
    "createdDate": "",
    "modifiedDateDate": ""
    "parents": [
        {
            "id": "user"
        }
    ],
    "permission": "700"
    "owners": [
        {
            "username": "user"
        }
    ]
}
*/
