package com.chakans.drive.domain.id;

import java.io.Serializable;
import java.util.Objects;

/**
 * An id for drive_personal_file_tree.
 */
public class DrivePersonalTreeId implements Serializable {

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

        DrivePersonalTreeId drivePersonalTreeId = (DrivePersonalTreeId) o;
        return Objects.equals(id, drivePersonalTreeId.getId())
            && Objects.equals(userLogin, drivePersonalTreeId.getUserLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userLogin);
    }
}
