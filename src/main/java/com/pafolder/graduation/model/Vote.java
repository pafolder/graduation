package com.pafolder.graduation.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.ProxyUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonFilter("voteJsonFilter")
@Table(name = "vote",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"vote_date", "user_id"}, name = "registered_user_idx")})
public class Vote {
    @Id
    @SequenceGenerator(name = "vote_id_generator", sequenceName = "vote_id_seq", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_id_generator")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", nullable = false, referencedColumnName = "id")
    @NotNull
    private Menu menu;

    @Column(name = "vote_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate voteDate;

    public Vote(User user, Menu menu) {
        this.user = user;
        this.menu = menu;
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
}
