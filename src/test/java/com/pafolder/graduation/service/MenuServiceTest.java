package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.pafolder.graduation.TestData.menu1;
import static com.pafolder.graduation.TestData.menu2;

class MenuServiceTest extends AbstractServiceTest{
    @Autowired
    private SPMenuService service;

    @Test
    void getAll() {
        service.addMenu(menu1);
        service.addMenu(menu2);
        service.getAll();
        for (Menu menu : service.getAll()) {
            log.error(menu.toString());
        }
    }

    @Test
    void addItem() {
    }

    @Test
    void addMenu() {
    }

    @Test
    void getByDate() {
    }
}