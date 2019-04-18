package com.chakans.drive.domain.id;

import java.io.Serializable;
import java.util.Objects;

/**
 * An id for drive_personal_file.
 */
public class DrivePersonalNodeId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userLogin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrivePersonalNodeId drivePersonalNodeId = (DrivePersonalNodeId) o;
        return Objects.equals(id, drivePersonalNodeId.getId())
            && Objects.equals(userLogin, drivePersonalNodeId.getUserLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userLogin);
    }
}
