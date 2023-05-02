CREATE TABLE member
(
    id       BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(50) NOT NULL,
    password VARCHAR(30) NOT NULL
);

CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`    VARCHAR(255) NOT NULL,
    image_url TEXT         NOT NULL,
    price     INT          NOT NULL
);

CREATE TABLE cart_product
(
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    UNIQUE (member_id, product_id)
);

INSERT INTO product(name, image_url, price) VALUES ('mouse', 'https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg', 100000);
INSERT INTO product(name, image_url, price) VALUES ('keyboard', 'https://i1.wp.com/blog.peoplefund.co.kr/wp-content/uploads/2020/01/진혁.jpg?fit=770%2C418&ssl=1', 250000);
INSERT INTO member(email, password) VALUES ('test@test.com', 'test');
INSERT INTO member(email, password) VALUES ('woowacourse@woowa.com', 'pobi');
