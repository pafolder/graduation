package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.repository.Menu.DataJpaMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class MenuService {
    private final DataJpaMenuRepository menuRepository;

    public MenuService(DataJpaMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Cacheable("user")
    public List<Menu> getAll() {
        return menuRepository.getAll();
    }

    public Menu addMenu(Menu menu) {
        return menuRepository.add(menu);
    }

    public List<Menu> getByDate(Date date) {
        return menuRepository.getByDate(date);
    }
}
