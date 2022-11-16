package com.pafolder.graduation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Entity(name = "Vote")
@Table(name = "vote",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "user_id"}, name = "vote_unique_date_user_idx")})
public class Vote {
    @Id
    @SequenceGenerator(name = "vote_id_generator", sequenceName = "vote_id_seq", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_id_generator")
    private Integer id;
    @Column(name = "date", nullable = false)
    private Date date;

    public Vote() {
    }

    public Vote(Date date, User user, Menu menu) {
        this.date = date;
        this.user = user;
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "\nVote " +
                "id=" + id +
                " " + date +
                " '" + user.getName() + '\'' +
                " '" + menu.getRestaurant() + '\'';
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull
    private Menu menu;
}
