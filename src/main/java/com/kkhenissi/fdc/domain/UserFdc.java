package com.kkhenissi.fdc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UserFdc.
 */
@Entity
@Table(name = "user_fdc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserFdc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_name_user")
    private String userNameUser;

    @Column(name = "first_name_user")
    private String firstNameUser;

    @Column(name = "last_name_user")
    private String lastNameUser;

    @Column(name = "email_user")
    private String emailUser;

    @Column(name = "password_user")
    private String passwordUser;

    @Column(name = "date_registration_user")
    private Instant dateRegistrationUser;

    @Column(name = "avatar_user")
    private String avatarUser;

    @Column(name = "status_user")
    private Boolean statusUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNameUser() {
        return userNameUser;
    }

    public UserFdc userNameUser(String userNameUser) {
        this.userNameUser = userNameUser;
        return this;
    }

    public void setUserNameUser(String userNameUser) {
        this.userNameUser = userNameUser;
    }

    public String getFirstNameUser() {
        return firstNameUser;
    }

    public UserFdc firstNameUser(String firstNameUser) {
        this.firstNameUser = firstNameUser;
        return this;
    }

    public void setFirstNameUser(String firstNameUser) {
        this.firstNameUser = firstNameUser;
    }

    public String getLastNameUser() {
        return lastNameUser;
    }

    public UserFdc lastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
        return this;
    }

    public void setLastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public UserFdc emailUser(String emailUser) {
        this.emailUser = emailUser;
        return this;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public UserFdc passwordUser(String passwordUser) {
        this.passwordUser = passwordUser;
        return this;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public Instant getDateRegistrationUser() {
        return dateRegistrationUser;
    }

    public UserFdc dateRegistrationUser(Instant dateRegistrationUser) {
        this.dateRegistrationUser = dateRegistrationUser;
        return this;
    }

    public void setDateRegistrationUser(Instant dateRegistrationUser) {
        this.dateRegistrationUser = dateRegistrationUser;
    }

    public String getAvatarUser() {
        return avatarUser;
    }

    public UserFdc avatarUser(String avatarUser) {
        this.avatarUser = avatarUser;
        return this;
    }

    public void setAvatarUser(String avatarUser) {
        this.avatarUser = avatarUser;
    }

    public Boolean isStatusUser() {
        return statusUser;
    }

    public UserFdc statusUser(Boolean statusUser) {
        this.statusUser = statusUser;
        return this;
    }

    public void setStatusUser(Boolean statusUser) {
        this.statusUser = statusUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserFdc)) {
            return false;
        }
        return id != null && id.equals(((UserFdc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserFdc{" +
            "id=" + getId() +
            ", userNameUser='" + getUserNameUser() + "'" +
            ", firstNameUser='" + getFirstNameUser() + "'" +
            ", lastNameUser='" + getLastNameUser() + "'" +
            ", emailUser='" + getEmailUser() + "'" +
            ", passwordUser='" + getPasswordUser() + "'" +
            ", dateRegistrationUser='" + getDateRegistrationUser() + "'" +
            ", avatarUser='" + getAvatarUser() + "'" +
            ", statusUser='" + isStatusUser() + "'" +
            "}";
    }
}
