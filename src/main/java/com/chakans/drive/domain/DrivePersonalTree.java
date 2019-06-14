package com.chakans.drive.domain;

import com.chakans.drive.domain.id.DrivePersonalTreeId;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A drive_personal_file_tree.
 */
@Entity
@IdClass(DrivePersonalTreeId.class)
@Table(name = "drive_personal_tree")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DrivePersonalTree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Id
    @Size(min = 1, max = 50)
    @Column(name = "user_login", length = 50, nullable = false, updatable = false)
    private String userLogin;

    @NotNull
    @Column(nullable = false)
    private Integer level;

    @Size(min = 1, max = 50)
    @Column(name = "parent_id", length = 50)
    private String parentId;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DrivePersonalTree)) {
            return false;
        }

        DrivePersonalTree drivePersonalTree = (DrivePersonalTree) o;
        return Objects.equals(id, drivePersonalTree.getId())
            && Objects.equals(userLogin, drivePersonalTree.getUserLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userLogin);
    }

    @Override
    public String toString() {
        return "DrivePersonalFileTree{" +
            "id='" + id + '\'' +
            ", userLogin='" + userLogin + '\'' +
            ", level=" + level +
            ", parentId='" + parentId + '\'' +
            "}";
    }
}
