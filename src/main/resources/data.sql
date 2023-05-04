CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    price     INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) references member (id),
    FOREIGN KEY (product_id) references product (id)
);

INSERT INTO product (id, name, image_url, price)
VALUES ('1',
        '치킨',
        'https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg',
        1000);

INSERT INTO product (id, name, image_url, price)
VALUES ('2',
        '피자',
        'https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/d440b8f4-91c3-4272-8a81-876e9aaffb9c/RisingStarGraphBox.jpg',
        10000);

INSERT INTO member (id, email, password)
VALUES ('1', 'a@a.com', 'password1');
INSERT INTO member (id, email, password)
VALUES ('2', 'b@b.com', 'password2');

INSERT INTO cart (member_id, product_id)
VALUES ('1', '1');
INSERT INTO cart (member_id, product_id)
VALUES ('1', '2');