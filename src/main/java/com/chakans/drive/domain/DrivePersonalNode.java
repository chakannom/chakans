package com.chakans.drive.domain;

import com.chakans.drive.domain.id.DrivePersonalNodeId;
import com.chakans.portal.domain.AbstractAuditingEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A drive_personal_file.
 */
@Entity
@IdClass(DrivePersonalNodeId.class)
@Table(name = "drive_personal_node")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DrivePersonalNode extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Id
    @Size(min = 1, max = 50)
    @Column(name = "user_login", length = 50, nullable = false, updatable = false)
    private String userLogin;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mime_type", length = 255, nullable = false)
    private String mimeType;

    @NotNull
    @Column(name = "sizeof", nullable = false)
    private Long size;

    @NotNull
    @Size(min = 3, max = 5)
    @Column(length = 5, nullable = false)
    private String permission;

    @Size(max = 255)
    @Column(length = 255)
    private String etag;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DrivePersonalNode drivePersonalNode = (DrivePersonalNode) o;
        return Objects.equals(id, drivePersonalNode.getId())
            && Objects.equals(userLogin, drivePersonalNode.getUserLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userLogin);
    }

    @Override
    public String toString() {
        return "DrivePersonalFile{" +
            "id='" + id + '\'' +
            ", userLogin='" + userLogin + '\'' +
            ", name='" + name + '\'' +
            ", mimeType='" + mimeType + '\'' +
            ", size=" + size +
            ", permission='" + permission + '\'' +
            ", etag='" + etag + '\'' +
            '}';
    }
}
