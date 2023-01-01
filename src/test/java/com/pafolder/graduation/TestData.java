package com.pafolder.graduation;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.model.Restaurant;
import com.pafolder.graduation.model.User;
import com.pafolder.graduation.model.Vote;

import java.sql.Date;

public class TestData {
    private static final String DEFAULT_PASSWORD = "password";
    public static Date DATE1 = Date.valueOf("2022-12-11");
    public static Date DATE2 = Date.valueOf("2022-12-12");
    public static Date DATE3 = Date.valueOf("2022-12-13");
    public static Date DATE4 = Date.valueOf("2022-12-14");
    public static Date DATE5 = Date.valueOf("2022-12-15");
    public static Date DATE6 = Date.valueOf("2022-12-16");
    public static Date DATE7 = Date.valueOf("2022-12-17");
    public static Restaurant RESTAURANT1 = new Restaurant("Старый ресторан", "Старая ул., дом 999");
    public static Restaurant RESTAURANT2 = new Restaurant("Первый ресторан", "Первый просп., дом 1");
    public static Restaurant RESTAURANT3 = new Restaurant("Второй ресторан", "Второй пер., дом 2");
    public static Restaurant RESTAURANT4 = new Restaurant("Третий ресторан", "Третий бульвар, дом 3");
    public static Restaurant RESTAURANT5 = new Restaurant("Четвёртый ресторан", "Четвёртая линия, дом 4");
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
    public static final User user = new User(0, "Иван Иванов", "ivan_ivanov@mail.net", DEFAULT_PASSWORD, User.Role.USER);
    public static final User updatedUser = new User(0, "Novoivanov", "newmail@nm.com", DEFAULT_PASSWORD, User.Role.ADMIN);
    public static final int NEW_USER_ID = 7;
    public static final User newUser = new User(0, "Новый Пользователь", "new_user@new_mail.new", DEFAULT_PASSWORD, User.Role.USER);
    public static final User admin = new User(4, "Пётр Петров", "petr_p@yandex.com", DEFAULT_PASSWORD, User.Role.ADMIN);
    public static final String NONEXISTING_ID_STRING = "-1";
    public static final Vote vote1 = new Vote(DATE6, admin, menu1);
}

