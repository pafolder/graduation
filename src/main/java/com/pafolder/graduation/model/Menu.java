package com.pafolder.graduation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"},
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

    @Column(name = "date", nullable = false)
    @NotNull
    private Date date;

    @NotNull
    @NotEmpty
    @ElementCollection(targetClass = Item.class)
    @CollectionTable(name = "menuitem", joinColumns = @JoinColumn(name = "menu_id"))
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
    public String toString() {
        String restaurantName = restaurant != null ? restaurant.getName() : "";
        StringBuilder sb = new StringBuilder("\n" + id + " " + restaurantName + " " + date);
        if (menuItems != null) {
            for (Item item : menuItems) {
                sb.append("\n").append(item.dishName).append(" ").append(item.dishPrice);
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    @Embeddable
    public static class Item {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu menu)) return false;
        return getId().equals(menu.getId()) && getRestaurant().equals(menu.getRestaurant()) && getDate().equals(menu.getDate()) && Objects.equals(getMenuItems(), menu.getMenuItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRestaurant(), getDate(), getMenuItems());
    }
}