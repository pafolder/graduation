DELETE FROM menus;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO menus (name, date)
VALUES ('Меню Старого Ресторана', '2020-01-30'),
       ('Меню Первого Ресторана', '2020-02-01'),
       ('Меню Второго Ресторана', '2020-02-01'),
       ('Меню Третьего Ресторана', '2020-02-01');
