INSERT INTO restaurant (name, address)
VALUES ('First Restaurant', '1 First Str.'),
       ('Second Restaurant', '2 Second Av.'),
       ('Third Restaurant', '3 Third Str.'),
       ('Fourth Restaurant', '4 Fourth Row'),
       ('The Old Restaurant', 'City Sq.');

INSERT INTO menu (restaurant_id, menu_date)
VALUES (5, CURRENT_DATE),
       (4, CURRENT_DATE),
       (2, CURRENT_DATE),
       (3, CURRENT_DATE),
       (1, CURRENT_DATE),
       (1, DATE_ADD(CURRENT_DATE, 1)),
       (3, DATE_ADD(CURRENT_DATE, 1)),
       (4, DATE_ADD(CURRENT_DATE, 1)),
       (5, DATE_SUB(CURRENT_DATE, 1));

INSERT INTO menu_item (menu_id, dish_name, dish_price)
VALUES (1, 'Stewed Cabbage', 88.99),
       (1, 'Three crusts of bread', 33.33),
       (2, 'Goulash', 140.00),
       (2, 'Fu agra', 438.00),
       (3, 'Vermicelli', 43.50),
       (3, 'Bechamel Sauce', 90.00),
       (3, 'Baked Apples', 200.00),
       (4, 'Plums', 183.30),
       (4, 'Caprese', 326.00),
       (4, 'Dry Red Wine', 450.00),
       (5, 'Snack of the day', 135.16),
       (5, 'Navy Pasta', 13.11),
       (5, 'Champagne', 599.00),
       (5, 'Strawberry dessert', 337.50),
       (6, 'Bean Salad', 77.14),
       (6, 'Green Borscht', 200.00),
       (6, 'Peanuts', 83.30),
       (7, 'Fish', 426.00),
       (7, 'Dry White Wine', 380.00),
       (8, 'Pineapple', 122.21),
       (8, 'Ox heart', 1345.67),
       (8, 'Dark Beer', 199.99),
       (9, 'Champagne', 599.00),
       (9, 'Pistachios', 338.50),
       (9, 'Appetizer of the day', 135.16);

INSERT INTO users (name, email, password, role)
VALUES ('Administrator', 'admin@mail.com', '{noop}admin', 'ADMIN'),
       ('User', 'user@mail.com', '{noop}password', 'USER'),
       ('John Smith', 'johnsmith@mail.net', '{noop}password', 'USER'),
       ('Alexandre Henderson', 'ahen@nomail.in', '{noop}password', 'USER'),
       ('Kirill Kirillov', 'kkirillov@qup.org', '{noop}password', 'USER'),
       ('Eugeni Flaccid', 'evgeniflaccid.e@yahoo.zoo', '{noop}password', 'USER');

INSERT INTO vote (user_id, menu_id, vote_date)
VALUES (1, 9, DATE_SUB(CURRENT_DATE, 1)),
       (6, 8, DATE_SUB(CURRENT_DATE, 1)),
       (2, 1, CURRENT_DATE),
       (3, 3, CURRENT_DATE),
       (4, 5, CURRENT_DATE),
       (5, 5, CURRENT_DATE);
