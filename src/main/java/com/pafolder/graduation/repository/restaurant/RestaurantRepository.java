package com.pafolder.graduation.repository.restaurant;

import com.pafolder.graduation.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
//    @Transactional
//    Restaurant save(Restaurant restaurant);

    List<Restaurant> findAll();
}
