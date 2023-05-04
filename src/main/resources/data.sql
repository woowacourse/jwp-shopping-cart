CREATE TABLE IF NOT EXISTS PRODUCT
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255)    NOT NULL,
    image      VARCHAR(2083),
    price      BIGINT UNSIGNED    NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MEMBER
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    email      VARCHAR(320) NOT NULL,
    password   VARCHAR(15) NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS CART
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    product_id BIGINT UNSIGNED NOT NULL,
    member_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT(id),
    FOREIGN KEY (member_id) REFERENCES MEMBER(id)
);

INSERT INTO MEMBER (email, password) VALUES ('rg970604@naver.com','password');
INSERT INTO MEMBER (email, password) VALUES ('yimsh66@naver.com','password');

INSERT INTO PRODUCT (name, image, price) VALUES ('치킨','https://nenechicken.com/17_new/images/menu/30005.jpg',18000);
INSERT INTO PRODUCT (name, image, price) VALUES ('피자','https://cdn.dominos.co.kr/admin/upload/goods/20230117_97ySneQn.jpg?RS=350x350&SP=1',21000);
INSERT INTO PRODUCT (name, image, price) VALUES ('막국수','https://gdimg.gmarket.co.kr/1797034760/still/600?ver=1621574924',9000);

