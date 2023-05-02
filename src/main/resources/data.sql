CREATE TABLE product
(
    id         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    price      INT         NOT NULL,
    image      TEXT        NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NULL
);

CREATE TABLE cart
(
    id         BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT    NOT NULL,
    member_id  BIGINT    NOT NULL,
    count      INT       NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL
);

CREATE TABLE member
(
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(50)  NOT NULL,
    phone      VARCHAR(20)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NULL
);

INSERT INTO product(name, price, image)
VALUES ('치킨', 10000, 'https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png');

INSERT INTO product(name, price, image)
VALUES ('후라이드 치킨', 20000, 'https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png');

INSERT INTO product(name, price, image)
VALUES ('맛있는 치킨', 30000, 'https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png');

INSERT INTO member(email, password, name, phone)
VALUES ('test@naver.com', '1234', 'juno', '010-1234-5678');

INSERT INTO member(email, password, name, phone)
VALUES ('test2@naver.com', '12345', 'why', '010-1111-2222');

INSERT INTO member(email, password, name, phone)
VALUES ('test3@naver.com', '123456', 'pobi', '010-3344-5543')
