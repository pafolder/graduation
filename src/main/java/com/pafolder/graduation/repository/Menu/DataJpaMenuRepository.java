package com.pafolder.graduation.repository.Menu;

import com.pafolder.graduation.model.Menu;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@ComponentScan
@Repository
public class DataJpaMenuRepository {
    private final MenuRepository menuRepository;

    public DataJpaMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    public void addItem(int menuId, Menu.Item menuItem) {
//        menuRepository.findById(menuId)
//                .map(m -> {
//                    m.addItem(menuItem);
//                    menuRepository.save(m);
//                    return m;
//                });
    }

    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

    public List<Menu> getByDate(Date date) {
        return menuRepository.findByDate(date);
    }

    @Transactional
    public boolean delete(int id) {
        return menuRepository.deleteById(id);
    }

    @Transactional
    public Menu add(Menu menu) {
        Menu existing = menuRepository.findByDateAndRestaurant(menu.getDate(), menu.getRestaurant());
        if (existing != null) {
            existing = menu;
            return existing;
        } else {
            return menuRepository.save(menu);
        }
    }
}
