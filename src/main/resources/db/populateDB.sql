DELETE
FROM menu;
ALTER SEQUENCE menu_id_seq RESTART WITH 0;
ALTER SEQUENCE user_id_seq RESTART WITH 0;
ALTER SEQUENCE vote_id_seq RESTART WITH 0;
ALTER SEQUENCE restaurant_id_seq RESTART WITH 0;

INSERT INTO restaurant (name, address)
VALUES ('Старый Ресторан', 'Старая ул., дом 999'),
       ('Первый Ресторан', 'Первый просп., дом 1'),
       ('Второй Ресторан', 'Второй пер., дом 2'),
       ('Третий Ресторан', 'Третий бульвар, дом 3'),
       ('Четвёртый ресторан', 'Четвёртая линия, дом 4');

INSERT INTO menu (restaurant_id, menu_date)
VALUES (0, '2022-12-16'),
       (1, '2022-12-17'),
       (2, '2022-12-17'),
       (3, '2022-12-17'),
       (1, '2022-12-16'),
       (2, '2022-12-16'),
       (3, '2022-12-16'),
       (4, '2022-12-16');

INSERT INTO menuitem (menu_id, dishname, dishprice)
VALUES (0, 'Фасоль', 88.99),
       (0, 'Три корочки хлеба', 33.33),
       (1, 'Гуляш', 140.00),
       (1, 'Фу агра', 438.00),
       (2, 'Вермишель', 43.50),
       (2, 'Соус Бешамель', 90.00),
       (2, 'Яблоки', 200.00),
       (3, 'Сливы', 183.30),
       (3, 'Капрезе', 326.00),
       (3, 'Вино красное сухое', 450.00),
       (4, 'Холодная закуска дня', 135.16),
       (4, 'Макароны по флотски', 13.11),
       (4, 'Шампанское', 599.00),
       (4, 'Десерт клубничный', 337.50),
       (5, 'Салат с фасолью', 77.14),
       (5, 'Борщ зелёный', 200.00),
       (5, 'Арахис', 83.30),
       (6, 'Рыба', 426.00),
       (6, 'Вино белое сухое', 380.00),
       (7, 'Ананас', 122.21),
       (7, 'Бычье сердце', 1345.67),
       (7, 'Пиво тёмное', 199.99);

INSERT INTO users (name, email, password, role)
VALUES ('Иван Иванов', 'ivan_ivanov@mail.net', '{noop}password', 'USER'),
       ('Серей Сергеев', 'sergey.sergeev@smail.su', '{noop}password', 'USER'),
       ('Александра Александрова', 'aa@nomail.ru', '{noop}password', 'USER'),
       ('Пётр Петров', 'petr_p@yandex.com', '{bcrypt}$2a$10$oHGXOKCZvICllyMwtvdNjeKHvx1EQXJgd5Ayh01S8vBGMAY3bvTOm',
        'ADMIN'),
       ('Кирилл Кириллов', 'k_kirillov@qq.org', '{noop}password', 'USER'),
       ('Евгения Евгеньевна', 'evgeniya_e@yahoo.zz', '{noop}password', 'USER');

INSERT INTO vote (user_id, menu_id, menu_date)
VALUES (0, 1, '2022-12-17'),
       (1, 0, '2022-12-16'),
       (2, 2, '2022-12-17'),
       (0, 4, '2022-12-16'),
       (3, 0, '2022-12-16'),
       (1, 2, '2022-12-17');