CREATE TABLE IF NOT EXISTS product
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL,
    image_url  VARCHAR(500) NOT NULL,
    price      INT unsigned NOT NULL,
    created_at DATETIME     NOT NULL default current_timestamp,
    updated_at DATETIME     NOT NULL default current_timestamp on update current_timestamp,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS member
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL default current_timestamp,
    updated_at DATETIME     NOT NULL default current_timestamp on update current_timestamp,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS cart
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    product_id BIGINT       NOT NULL,
    member_id  VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL default current_timestamp,
    updated_at DATETIME     NOT NULL default current_timestamp on update current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
    );

insert into MEMBER(email, password)
values ('emailA@naver.com', 'passwordA'),
       ('emailB@kakao.com', 'passwordB');

insert into PRODUCT(name, price, image_url)
values ('MacBook Air 13인치', 1000000,
        'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-air-midnight-select-20220606?wid=904&hei=840&fmt=jpeg&qlt=90&.v=1653084303665'),
       ('NC 다이노스 홈 어센틱 유니폼', 140000,
        'https://i.namu.wiki/i/1dMIJjKDpMK39KhyChiHr0wJ9AN3BCbhD8ILDzsmAqaErwoDFcJI2LkHxDrztgNFOc0hQCIgjYPFd_AiFVv-kBxwinCGBkQ_1jgoFx6EgqrtY1rSllVW-Zu7U0-xoy8R9BNOc8WOsfJhi_8kll7LKg.webp');
