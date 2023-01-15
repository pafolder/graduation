package com.pafolder.graduation.to;

import com.pafolder.graduation.model.Menu;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MenuTo {
    @NotNull
    private Integer restaurantId;

    @Nullable
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotEmpty
    private List<Menu.Item> menuItems;
}
