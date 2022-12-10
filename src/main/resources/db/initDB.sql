DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS menuitem;
DROP SEQUENCE IF EXISTS menu_id_seq;
DROP SEQUENCE IF EXISTS user_id_seq;
DROP SEQUENCE IF EXISTS vote_id_seq;

CREATE SEQUENCE menu_id_seq START WITH 0;
CREATE SEQUENCE user_id_seq START WITH 0;
CREATE SEQUENCE vote_id_seq START WITH 0;

CREATE TABLE menu
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE menu_id_seq PRIMARY KEY,
    restaurant VARCHAR(256) NOT NULL,
    date       DATE         NOT NULL
);

CREATE UNIQUE INDEX menu_unique_restaurant_date_idx
    ON menu (restaurant, date);

CREATE TABLE menuitem
(
    menu_id   INTEGER,
    dishname  VARCHAR(256),
    dishprice REAL
);

CREATE TABLE user
(
    id       INTEGER GENERATED BY DEFAULT AS SEQUENCE user_id_seq PRIMARY KEY,
    name     VARCHAR(256) NOT NULL,
    email    VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    enabled  BOOLEAN DEFAULT TRUE,
    role     VARCHAR(256)
);

CREATE UNIQUE INDEX user_unique_email_idx ON user (email);

CREATE TABLE role
(
    user_id INTEGER,
    role    VARCHAR(256)
);

CREATE TABLE vote
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE vote_id_seq PRIMARY KEY,
    date    DATE    NOT NULL,
    user_id INTEGER NOT NULL,
    menu_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE,
    CONSTRAINT vote_unique_date_user_idx UNIQUE (date, user_id)
);
