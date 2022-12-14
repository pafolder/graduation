DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS menuitem;
DROP TABLE IF EXISTS restaurant;
DROP SEQUENCE IF EXISTS menu_id_seq;
DROP SEQUENCE IF EXISTS user_id_seq;
DROP SEQUENCE IF EXISTS vote_id_seq;
DROP SEQUENCE IF EXISTS restaurant_id_seq;

CREATE SEQUENCE menu_id_seq START WITH 0;
CREATE SEQUENCE user_id_seq START WITH 0;
CREATE SEQUENCE vote_id_seq START WITH 0;
CREATE SEQUENCE restaurant_id_seq START WITH 0;

CREATE TABLE restaurant
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE restaurant_id_seq PRIMARY KEY,
    name    VARCHAR(256),
    address VARCHAR(256),
    CONSTRAINT restaurant_unique_name_address_idx UNIQUE (name, address)
);

CREATE TABLE menu
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE menu_id_seq PRIMARY KEY,
    restaurant_id INTEGER NOT NULL,
    menu_date          DATE    NOT NULL,
    CONSTRAINT menu_unique_restaurant_date_idx UNIQUE (restaurant_id, menu_date),
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT (id) ON DELETE CASCADE
);

CREATE TABLE menuitem
(
    menu_id   INTEGER,
    dishname  VARCHAR(256),
    dishprice REAL
);

CREATE TABLE users
(
    id       INTEGER GENERATED BY DEFAULT AS SEQUENCE user_id_seq PRIMARY KEY,
    name     VARCHAR(256) NOT NULL,
    email    VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    enabled  BOOLEAN DEFAULT TRUE,
    role     VARCHAR(256)
);

CREATE UNIQUE INDEX user_unique_email_idx ON USERS (email);

CREATE TABLE role
(
    user_id INTEGER,
    role    VARCHAR(256)
);

CREATE TABLE vote
(
    id        INTEGER GENERATED BY DEFAULT AS SEQUENCE vote_id_seq PRIMARY KEY,
    user_id   INTEGER NOT NULL,
    menu_id   INTEGER NOT NULL,
    menu_date DATE    NOT NULL,
    CONSTRAINT vote_unique_date_user_idx UNIQUE (menu_date, user_id),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES MENU (id) ON DELETE CASCADE
);
