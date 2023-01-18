package com.pafolder.graduation.to;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
public class RestaurantTo {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    private int voteCount;
}
