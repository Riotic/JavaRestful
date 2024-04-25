package com.quest.etna.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.Objects;



@Entity // This tells Hibernate to make a table out of this class - lien du tuto: https://spring.io/guides/gs/accessing-data-mysql et https://www.baeldung.com/jpa-unique-constraints
@Table
public class User {

    public enum UserRole {
        ROLE_USER,
        ROLE_ADMIN
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(unique=true, nullable=false, length=255)
    private String username;

    @Column(nullable=false, length=255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length=255)
    private UserRole role = UserRole.ROLE_USER;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate = new Date();


    // hibernate a besoin d'un constructeur sans argument autrement il crée une erreur serveur et je pete mon crane
    public User() {
    }   

    public User(Integer id, String username, String password, UserRole role, Date creationDate, Date updatedDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(creationDate, user.creationDate) && Objects.equals(updatedDate, user.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role, creationDate, updatedDate);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", role='" + getRole() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }


}