package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.repository.menu.DataJpaMenuRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    private final DataJpaMenuRepository menuRepository;

    public MenuService(DataJpaMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAll() {
        return menuRepository.getAll();
    }

    public Menu add(Menu menu) {
        return menuRepository.add(menu);
    }

    public List<Menu> getByDate(Date date) {
        return menuRepository.getByDate(date);
    }

    public Optional<Menu> getById(int id) {
        return menuRepository.getById(id);
    }

    public Menu getByDateAndRestaurant(Date date, Restaurant restaurant) {
        return menuRepository.getByDateAndRestaurant(date, restaurant);
    }
}
