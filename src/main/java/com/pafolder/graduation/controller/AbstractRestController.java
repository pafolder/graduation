package com.pafolder.graduation.controller;

import com.pafolder.graduation.service.MenuService;
import com.pafolder.graduation.validator.MenuValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class AbstractRestController {
    @Autowired
    protected MenuService menuService;

    @Autowired
    private MenuValidator menuValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(menuValidator);
    }

    protected Logger log = LoggerFactory.getLogger("yellow");

}
