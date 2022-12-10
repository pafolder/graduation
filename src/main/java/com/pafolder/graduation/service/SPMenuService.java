package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.repository.Menu.DataJpaMenuRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@ComponentScan
@Service
public class SPMenuService {
    @Autowired
    private DataJpaMenuRepository menuRepository;

    @Cacheable("user")
    public List<Menu> getAll() {
        return menuRepository.getAll();
    }

    @CacheEvict("user")
    public void addItem(int menuId, String name, Double price) {
        menuRepository.addItem(menuId, new Menu.@Valid Item(name, price));
    }

    public Menu addMenu(Menu menu) {
        return menuRepository.add(menu);
    }

    public List<Menu> getByDate(Date date) {
        return menuRepository.getByDate(date);
    }
}
