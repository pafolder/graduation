package com.pafolder.graduation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.util.ProxyUtils;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "vote",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"registered", "user_id"}, name = "registered_user_idx")})
public class Vote implements Serializable {
    @Id
    @SequenceGenerator(name = "vote_id_generator", sequenceName = "vote_id_seq", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_id_generator")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false, referencedColumnName = "id")
    @NotNull
    private Menu menu;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    private @NotNull Timestamp registered;

    public Vote() {
    }

    public Vote(User user, Menu menu) {
        this.user = user;
        this.menu = menu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public @NotNull Timestamp getRegistered() {
        return registered;
    }

    public void setRegistered(@NotNull Timestamp registered) {
        this.registered = registered;
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
        return "\nVote { id=" + id + ", " +
                (menu != null ? menu.toString() : " Menu {}, ") +
                (user != null ? user.toString() : " User {}, registered=") +
                (registered != null ? registered.toString() : "");
    }
}
