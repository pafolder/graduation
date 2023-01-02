package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.Restaurant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Transactional
    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(int id);

    List<Restaurant> findAll();

    @Override
    void deleteById(Integer integer);
}
