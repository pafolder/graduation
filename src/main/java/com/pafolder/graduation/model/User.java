package com.pafolder.graduation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity(name = "User")
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "user_unique_email_idx")})
public class User {
    @Id
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_seq", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")

    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "email", nullable = false)
    @NotNull
    @NotEmpty
    @Email
//    @Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "\nUser{" + id +
                " '" + name + '\'' +
                " '" + email + '\'' +
                " role=" + role +
                '}';
    }


    public enum Role {
        CLIENT,
        ADMIN
    }
}
