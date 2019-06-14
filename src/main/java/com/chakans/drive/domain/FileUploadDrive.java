package com.chakans.drive.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A file_upload_drive.
 */
@Entity
@Table(name = "file_upload_drive")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileUploadDrive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "directory", nullable = false, updatable = false)
    private String directory;

    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileUploadDrive)) {
            return false;
        }
        return directory != null && directory.equals(((FileUploadDrive) o).directory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directory);
    }

    @Override
    public String toString() {
        return "FileUploadDrive{" +
            "directory='" + directory + '\'' +
            "}";
    }

}
