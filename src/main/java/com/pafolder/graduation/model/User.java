package com.pafolder.graduation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity(name = "User")
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "user_unique_email_idx")})
public class User {
    @Id
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_seq", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")

    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    @NotEmpty
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(Integer id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "\nUser{" + id +
                " '" + name + '\'' +
                " '" + email + '\'' +
                " '" + password + '\'' +
                " role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && getName().equals(user.getName()) && getEmail().equals(user.getEmail()) && password.equals(user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), password, role);
    }

    public enum Role implements GrantedAuthority {
        USER,
        ADMIN;

        @Override
        public String getAuthority() {
            return "ROLE_" + name();
        }
    }
}
