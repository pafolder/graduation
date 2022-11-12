package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.repository.DataJpaMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private final DataJpaMenuRepository menuRepository;

    @Autowired
    public MenuService(DataJpaMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAll() {
        return menuRepository.getAll();
    }
}
