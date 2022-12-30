package com.pafolder.graduation.service;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.repository.MenuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.pafolder.graduation.TestData.menu1;
import static com.pafolder.graduation.TestData.menu2;

class MenuServiceTest extends AbstractServiceTest{
    @Autowired
    private MenuRepository menuRepository;

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