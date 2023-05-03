CREATE TABLE IF NOT EXISTS ITEM
(
    item_id     BIGINT              AUTO_INCREMENT  NOT NULL,
    name        VARCHAR(255)        NOT NULL,
    image_url   VARCHAR(255)        NOT NULL,
    price       INT                 NOT NULL,

    primary key (item_id)
);

CREATE TABLE IF NOT EXISTS MEMBER
(
    member_id   BIGINT              AUTO_INCREMENT  NOT NULL,
    email       VARCHAR(255)        UNIQUE NOT NULL,
    password    VARCHAR(255)        NOT NULL,
    name        VARCHAR(255)        NOT NULL,

    primary key (member_id)
);

CREATE TABLE IF NOT EXISTS CART
(
    cart_id       BIGINT    AUTO_INCREMENT  NOT NULL,
    member_id     BIGINT    NOT NULL,
    item_id       BIGINT    NOT NULL,

    primary key (cart_id),
    foreign key (item_id) references item (item_id),
    foreign key (member_id) references member (member_id)
);


INSERT INTO ITEM(name, image_url, price) VALUES ('자전거1', 'https://www.altonsports.com/prdimg/get/21-INNOZEN24_P_01%281060X600%29.jpg', 10000);
INSERT INTO ITEM(name, image_url, price) VALUES ('자전거2', 'https://cdn.imweb.me/thumbnail/20220817/7b35b82e7c1ce.jpg', 50000);
INSERT INTO ITEM(name, image_url, price) VALUES ('자전거3', 'https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2022/01/25/11/8/3294fc8a-92a5-410b-b3bb-fb3c7e18a1d9.jpg', 100000);

INSERT INTO MEMBER(email, password, name) VALUES ('aaa@aaa.com', 'helloSpring', 'gray');
INSERT INTO MEMBER(email, password, name) VALUES ('bbb@bbb.com', 'helloKotlin', 'zito');
INSERT INTO MEMBER(email, password, name) VALUES ('ccc@ccc.com', 'helloJava', 'jaemy');

INSERT INTO CART(cart_id, member_id, item_id) VALUES ('1', '1', '1');
INSERT INTO CART(cart_id, member_id, item_id) VALUES ('2', '1', '2');
INSERT INTO CART(cart_id, member_id, item_id) VALUES ('3', '2', '1');
