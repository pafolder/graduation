package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.repository.restaurant.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public void add(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> getById(Integer id) {
        return restaurantRepository.findById(id);
    }
}
