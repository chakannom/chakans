package com.chakans.portal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A file_upload_album.
 */
@Entity
@Table(name = "cks_file_upload_album")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileUploadAlbum implements Serializable {

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
        if (!(o instanceof FileUploadAlbum)) {
            return false;
        }
        return Objects.equals(directory, ((FileUploadAlbum) o).directory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directory);
    }

    @Override
    public String toString() {
        return "BlogFileUpload{" +
            "directory='" + directory + '\'' +
            "}";
    }
}