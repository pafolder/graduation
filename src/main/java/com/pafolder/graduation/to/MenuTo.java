package com.pafolder.graduation.to;

import com.pafolder.graduation.model.Menu;

import java.util.List;

public class MenuTo {
    private String name;
    private List<Menu.Item> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Menu.Item> getList() {
        return list;
    }

    public void setList(List<Menu.Item> list) {
        this.list = list;
    }
}
