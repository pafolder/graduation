package com.pafolder.graduation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Entity
@Table(name = "vote",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_date", "user_id"}, name = "vote_unique_date_user_idx")})
public class Vote {
    @Id
    @SequenceGenerator(name = "vote_id_generator", sequenceName = "vote_id_seq", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_id_generator")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "menu_id", nullable = false, referencedColumnName = "id"),
            @JoinColumn(name = "menu_date", nullable = false, referencedColumnName = "date")
    })
    @NotNull
    private Menu menu;

    public Vote() {
    }

    public Vote(Date date, User user, Menu menu) {
//        this.date = date;
        this.user = user;
        this.menu = menu;
    }

    @Override
    public String toString() {
        String dateString = (menu != null) ? menu.getDate().toString() : "";
        String restaurantName = (menu != null && menu.getRestaurant() != null) ?
                menu.getRestaurant().getName() : "";
        String userName = user != null ? user.getName() : "";

        return "\nVote " +
                "id=" + id +
                " " + dateString +
                " '" + userName + "'" +
                " '" + restaurantName + "'";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return menu.getDate();
    }

//    public void setDate(Date date) {
//        this.date = date;
//    }

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
}
