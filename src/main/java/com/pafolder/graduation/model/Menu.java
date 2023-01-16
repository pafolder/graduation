package com.pafolder.graduation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.util.ProxyUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "menu_date"},
        name = "menu_unique_restaurant_date_idx")})
public class Menu {
    @Id
    @SequenceGenerator(name = "menu_id_seq", sequenceName = "menu_id_seq", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_id_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "menu_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate menuDate;

    @NotEmpty
    @ElementCollection(targetClass = Item.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "menu_item", joinColumns = @JoinColumn(name = "menu_id"))
    private List<Item> menuItems;

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

    @Embeddable
    @Getter
    @Setter
    @ToString
    public static class Item {
        @Column(name = "dish_name", nullable = false)
        @NotBlank
        private String dishName;

        @Column(name = "dish_price", nullable = false, precision = 7, scale = 2)
        @Digits(integer = 9, fraction = 2)
        private BigDecimal dishPrice;
    }
}
