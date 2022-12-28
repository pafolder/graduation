package com.pafolder.graduation.repository.restaurant;

import com.pafolder.graduation.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaRestaurantRepository {
    private final RestaurantRepository restaurantRepository;

    public DataJpaRestaurantRepository(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void save(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
