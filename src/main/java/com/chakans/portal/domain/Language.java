package com.chakans.portal.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A language
 */
@Entity
@Table(name = "cks_language")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 10)
    @Id
    @Column(length = 10)
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Language)) {
            return false;
        }
        return Objects.equals(key, ((Language) o).key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "Language{" +
            "key='" + key + '\'' +
            "}";
    }
}