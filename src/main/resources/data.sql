CREATE TABLE IF NOT EXISTS users (
    id          INT           AUTO_INCREMENT,
    email       VARCHAR(100)  NOT NULL,
    password    VARCHAR       NOT NULL,
    name        VARCHAR(20)   DEFAULT '익명의 사용자',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product (
    id       INT           AUTO_INCREMENT,
    name     VARCHAR(20)   NOT NULL,
    image    VARCHAR       NOT NULL,
    price    INT           NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cart (
    id          INT       AUTO_INCREMENT,
    user_id     INT       NOT NULL,
    product_id  INT       NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO users(email, password, name) VALUES ('ditoo@wooteco.com', 'ditoo1234', '디투'),
                         ('barrel@wooteco.com', 'barrel1234', '배럴'),
                         ('brown@wooteco.com', 'brown1234', '브라운');
INSERT INTO users(email, password) VALUES ('anonymous@wooteco.com', '12345678');
