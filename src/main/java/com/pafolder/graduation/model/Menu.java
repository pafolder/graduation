package com.pafolder.graduation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.util.ProxyUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "menu_date"},
        name = "menu_unique_restaurant_date_idx")})
public class Menu implements Serializable {
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
    private Date date;

    @NotNull
    @NotEmpty
    @ElementCollection(targetClass = Item.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "menu_item", joinColumns = @JoinColumn(name = "menu_id"))
    private List<Item> menuItems;

    public void setMenuItems(List<Item> menuItems) {
        this.menuItems = menuItems;
    }

    public Menu() {
    }

    public Menu(Restaurant restaurant, Date date, Menu.Item... items) {
        this.restaurant = restaurant;
        this.date = date;
        menuItems = new ArrayList<>();
        addItems(items);
    }

    public Menu(Restaurant restaurant, Date date, List<Menu.Item> items) {
        this.restaurant = restaurant;
        this.date = date;
        this.menuItems = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Item> getMenuItems() {
        return menuItems;
    }

    public void addItems(Item... items) {
        List<Item> itemList = Arrays.asList(items);
        menuItems.addAll(itemList);
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
        StringBuilder sb = new StringBuilder("Menu { id=" + id + ", " +
                (restaurant != null ? restaurant.toString() : "Restaurant {}") + ", date='" + date + "'");
        if (menuItems != null) {
            for (Item item : menuItems) {
                sb.append(", Item { ").append(item.dishName).append(", ").append(item.dishPrice).append(" }");
            }
        }
        return sb.toString();
    }

    @Embeddable
    public static class Item implements Serializable {
        @NotNull
        @NotBlank
        private String dishName;

        @NotNull
        @Range(min = 0, max = 10000)
        private Double dishPrice;

        public Item() {
        }

        public Item(String dishName, Double dishPrice) {
            this.dishName = dishName;
            this.dishPrice = dishPrice;
        }

        public String getDishName() {
            return dishName;
        }

        public void setDishName(String dishName) {
            this.dishName = dishName;
        }

        public Double getDishPrice() {
            return dishPrice;
        }

        public void setDishPrice(Double dishPrice) {
            this.dishPrice = dishPrice;
        }
    }
}
