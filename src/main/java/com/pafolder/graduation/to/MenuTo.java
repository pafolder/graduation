package com.pafolder.graduation.to;

import com.pafolder.graduation.model.Menu;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.sql.Date;
import java.util.List;

public class MenuTo {
    @NotNull
    private Integer restaurantId;

    @Nullable
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    @NotEmpty
    private List<Menu.Item> menuItems;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(@Nullable Integer restaurantId) {
        this.restaurantId = restaurantId;
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
