package com.pafolder.graduation.repository;

import com.pafolder.graduation.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaMenuRepository {
    private final MenuRepository menuRepository;

    public DataJpaMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAll() {
        return menuRepository.findAll();
    }
}
