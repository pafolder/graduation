DELETE
FROM menu;
ALTER SEQUENCE menu_id_seq RESTART WITH 0;
ALTER SEQUENCE user_id_seq RESTART WITH 0;
ALTER SEQUENCE vote_id_seq RESTART WITH 0;
ALTER SEQUENCE restaurant_id_seq RESTART WITH 0;

INSERT INTO restaurant (name, address)
VALUES ('Старый Ресторан', 'x'),
       ('Первый Ресторан', 'x'),
       ('Второй Ресторан', 'x'),
       ('Третий Ресторан', 'x');

INSERT INTO menu (restaurant_id, date)
VALUES (0, '2020-01-30'),
       (1, '2020-02-01'),
       (2, '2020-02-01'),
       (3, '2020-02-01');

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

INSERT INTO users (name, email, password, role)
VALUES ('Иван Иванов', 'ivan_ivanov@mail.net', 'password', 'USER'),
       ('Серей Сергеев', 'Segey.Sergeev@dvorkin.ru', 'password', 'USER'),
       ('Александра Александрова', 'aa@nomail.su', 'password', 'USER'),
       ('Пётр Петров', 'petr_p@yandex.com', 'password', 'ADMIN'),
       ('Кирилл Кириллов', 'k_kirillov@qq.org', 'password', 'USER'),
       ('Евгения Евгеньевна', 'evgeniya_e@yahoo.kz', 'password', 'USER');

INSERT INTO vote (date, user_id, menu_id)
VALUES ('2020-02-01', 0, 1),
       ('2020-02-01', 1, 0),
       ('2020-02-02', 2, 2),
       ('2020-02-02', 3, 3),
       ('2020-02-02', 4, 0),
       ('2020-02-02', 5, 0);