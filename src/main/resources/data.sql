CREATE TABLE if not exists PRODUCT
(
    id        BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    name      VARCHAR(100)                   NOT NULL,
    price     INT                            NOT NULL,
    image_url VARCHAR(255)                   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE if not exists MEMBER
(
    id       BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    email    VARCHAR(100) unique            NOT NULL,
    password VARCHAR(255)                   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE if not exists CART_PRODUCT
(
    id         BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    member_id  BIGINT UNSIGNED                NOT NULL,
    product_id BIGINT UNSIGNED                NOT NULL,
    FOREIGN KEY (member_id) REFERENCES MEMBER (id) on delete CASCADE,
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id) on delete CASCADE
);


insert into MEMBER(email, password)
values ('emailA@naver.com', 'passwordA'),
       ('emailB@kakao.com', 'passwordB');

insert into PRODUCT(name, price, image_url)
values ('MacBook Air 13인치', 1000000,
        'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-air-midnight-select-20220606?wid=904&hei=840&fmt=jpeg&qlt=90&.v=1653084303665'),
       ('NC 다이노스 홈 어센틱 유니폼', 140000,
        'https://i.namu.wiki/i/1dMIJjKDpMK39KhyChiHr0wJ9AN3BCbhD8ILDzsmAqaErwoDFcJI2LkHxDrztgNFOc0hQCIgjYPFd_AiFVv-kBxwinCGBkQ_1jgoFx6EgqrtY1rSllVW-Zu7U0-xoy8R9BNOc8WOsfJhi_8kll7LKg.webp');

