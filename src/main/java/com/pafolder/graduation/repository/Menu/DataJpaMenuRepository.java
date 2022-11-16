package com.pafolder.graduation.repository.Menu;

import com.pafolder.graduation.model.Menu;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.sql.Date;
import java.util.List;

@Repository
@Validated
public class DataJpaMenuRepository {
    private final MenuRepository menuRepository;

    public DataJpaMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

    public List<Menu> getByDate(Date date) {
        return menuRepository.findByDate(date);
    }

    @Transactional
    public void addItem(int menuId, @Valid Menu.Item menuItem) {
        menuRepository.findById(menuId)
                .map(m -> {
                    m.addItem(menuItem);
                    menuRepository.save(m);
                    return m;
                });
    }

    @Transactional
    public void save(Menu menu) {
        menuRepository.save(menu);
    }
}
