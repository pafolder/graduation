package com.pafolder.graduation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.util.ProxyUtils;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(max = 256)
    @NotEmpty
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Integer id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        return id != null;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    @Override
    public String toString() {
        return "\nUser { id='" + id + "', name='" + (name != null ? name : "") + "', email='" +
                (email != null ? email : "") + "', role='" + (role != null ? role : "") + "' }";
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
