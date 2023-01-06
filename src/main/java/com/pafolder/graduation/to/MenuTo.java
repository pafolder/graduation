package com.pafolder.graduation.to;

import com.pafolder.graduation.model.Menu;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.sql.Date;
import java.util.List;

public class MenuTo {
    @Nullable
    private Integer restaurantId;

    @Nullable
    private String restaurantName;

    @Nullable
    private String restaurantAddress;

    @Nullable
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    @NotEmpty
    private List<Menu.Item> menuItems;

    @Nullable
    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(@Nullable Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Nullable
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(@Nullable String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Nullable
    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(@Nullable String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    @Nullable
    public Date getDate() {
        return date;
    }

    public void setDate(@Nullable Date date) {
        this.date = date;
    }

    public List<Menu.Item> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<Menu.Item> menuItems) {
        this.menuItems = menuItems;
    }
}
