DELETE FROM menu;
ALTER SEQUENCE menu_id_seq RESTART WITH 0;
ALTER SEQUENCE user_id_seq RESTART WITH 1;
ALTER SEQUENCE vote_id_seq RESTART WITH 0;

INSERT INTO menu (restaurant, date)
VALUES ('Старый Ресторан', '2020-01-30'),
       ('Первый Ресторан', '2020-02-01'),
       ('Второй Ресторан', '2020-02-01'),
       ('Третий Ресторан', '2020-02-01');

INSERT INTO menuitem (menu_id, dishname, dishprice)
VALUES (0, 'Фасоль', 99.99),
       (0, 'Рис', 88.00),
       (1, 'Гуляш', 140.00),
       (1, 'Фу агра', 438.00),
       (2, 'Вермишель', 43.50),
       (2, 'Соус Бешамель', 90.00),
       (2, 'Яблоки', 200.00),
       (3, 'Сливы', 183.30),
       (3, 'Капрезе', 326.00),
       (3, 'Вино красное сухое', 450.00);

INSERT INTO user (name, email, role)
VALUES ('Иван Иванов', 'ivan_ivanov@mail.net', 'CLIENT'),
       ('Серей Сергеев', 'Segey.Sergeev@dvorkim.ru', 'CLIENT'),
       ('Александра Александрова', 'aa@nomail.su', 'CLIENT'),
       ('Пётр Петров', 'petr_p@yandex.com', 'ADMIN'),
       ('Кирилл Кириллов', 'k_kirillov@qq.org', 'CLIENT'),
       ('Евгения Евгеньева', 'evgeniya_e@yahoo.kz', 'CLIENT');

INSERT INTO vote (date, user_id, menu_id)
VALUES ('2020-01-30', 1, 3),
       ('2020-02-01', 2, 1);