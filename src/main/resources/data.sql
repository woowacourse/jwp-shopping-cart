DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS cart;

CREATE TABLE product
(
    id      BIGINT AUTO_INCREMENT,
    name    VARCHAR(45) NOT NULL,
    price   INT         NOT NULL,
    img_url CLOB(10K) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id       BIGINT AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id         BIGINT AUTO_INCREMENT,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

INSERT INTO product(id, name, price, img_url)
VALUES (1, '상품명 테스트1', 100, 'https://cdn.pixabay.com/photo/2014/06/03/19/38/test-361512_960_720.jpg'),
       (2, '상품명 테스트2', 101, 'https://t1.daumcdn.net/cartoon/7520d517fbe0271cdceb64e57ff1b26aa2abb680');

INSERT INTO member(id, email, password)
VALUES (1, 'test1@test.com', '!!abc123'),
       (2, 'test2@test.com', '!!abc123')
