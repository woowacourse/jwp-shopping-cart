CREATE TABLE IF NOT EXISTS PRODUCT
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(30)  NOT NULL,
    image_url VARCHAR(max) NOT NULL,
    price     INT          NOT NUll
);

CREATE TABLE IF NOT EXISTS MEMBER
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS CART
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL
);

INSERT INTO member (email, password)
VALUES ('email1@email2', 'password1');
INSERT INTO member (email, password)
VALUES ('email2@email3', 'password2');
insert into product (name, image_url, price)
VALUES ('피자',
        'https://www.7thpizza.com/files/MENU/3F6493546AEC446B980E975410DFB1EB-fc635fde408c3030a8f06ef173ab8260.jpg',
        20000);
insert into product (name, image_url, price)
VALUES ('치킨',
        'https://i.namu.wiki/i/pTVoWDp5G09PGTRUTbCy8raXo9CB47uF2wcuzdUYTlPwRjU6zjl0Reoih4MIXXRTnfxVl-yKlPjTQSVhAbfSxA.webp',
        30000);
