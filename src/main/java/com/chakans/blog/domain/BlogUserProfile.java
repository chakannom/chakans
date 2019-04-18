package com.chakans.blog.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chakans.portal.domain.AbstractAuditingEntity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A blog_user_profile.
 */
@Entity
@Table(name = "blog_user_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlogUserProfile extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(min = 1, max = 50)
    @Column(name = "user_login", length = 50, nullable = false, updatable = false)
    private String userLogin;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String nickname;

    @NotNull
    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, nullable = false)
    private String email;

    @Size(max = 512)
    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @NotNull
    @Column(name = "opened_profile",nullable = false)
    private boolean openedProfile = false;

    @NotNull
    @Column(name = "opened_email", nullable = false)
    private boolean openedEmail = false;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isOpenedProfile() {
        return openedProfile;
    }

    public void setOpenedProfile(boolean openedProfile) {
        this.openedProfile = openedProfile;
    }

    public boolean isOpenedEmail() {
        return openedEmail;
    }

    public void setOpenedEmail(boolean openedEmail) {
        this.openedEmail = openedEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BlogUserProfile blogUserProfile = (BlogUserProfile) o;
        return Objects.equals(userLogin, blogUserProfile.getUserLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userLogin);
    }

    @Override
    public String toString() {
        return "BlogUserProfile{" +
            "userLogin='" + userLogin + '\'' +
            ", nickname='" + nickname + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", openedProfile=" + openedProfile +
            ", openedEmail=" + openedEmail +
            '}';
    }
}
