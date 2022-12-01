package com.pafolder.graduation;

import com.pafolder.graduation.model.Menu;

import java.sql.Date;

public class TestData {
    public static Date DATE1 = Date.valueOf("2022-12-11");
    public static Date DATE2 = Date.valueOf("2022-12-12");
    public static Date DATE3 = Date.valueOf("2022-12-13");
    public static Date DATE4 = Date.valueOf("2022-12-14");
    public static Date DATE5 = Date.valueOf("2022-12-15");
    public static Date DATE6 = Date.valueOf("2022-12-16");
    public static Date DATE7 = Date.valueOf("2022-12-17");
    public static String RESTAURANT1 = "Первый ресторан";
    public static String RESTAURANT2 = "Второй ресторан";
    public static String RESTAURANT3 = "Третий ресторан";
    public static String RESTAURANT4 = "Четвёртый ресторан";
    public static String RESTAURANT5 = "Пятый ресторан";
    public static final Menu.Item menuItem1 = new Menu.Item("Сосиски", 129.80);
    public static final Menu.Item menuItem2 = new Menu.Item("Макароны", 67.50);
    public static final Menu.Item menuItem3 = new Menu.Item("Вино сухое", 380.00);
    public static final Menu.Item menuItem4 = new Menu.Item("Вода минеральная", 90.00);
    public static final Menu.Item menuItem5 = new Menu.Item("Мидии в горчичном соусе", 460.30);
    public static final Menu.Item menuItem6 = new Menu.Item("Маш", 80.80);
    public static final Menu.Item menuItem7 = new Menu.Item("Компот", 70.00);
    public static final Menu.Item menuItem8 = new Menu.Item("Баклажаны", 220.00);
    public static final Menu menu1 = new Menu(RESTAURANT1, DATE1, menuItem1, menuItem2, menuItem3);
    public static final Menu menu2 = new Menu(RESTAURANT2, DATE1, menuItem4, menuItem5);
}
