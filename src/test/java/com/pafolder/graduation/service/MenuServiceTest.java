package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Menu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.pafolder.graduation.TestData.menu1;
import static com.pafolder.graduation.TestData.menu2;

class MenuServiceTest extends AbstractServiceTest{
    @Autowired
    private MenuService service;

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