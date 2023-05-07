CREATE TABLE product
(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50) NOT NULL,
    price     BIGINT      NOT NULL,
    image_url TEXT        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(255),
    phone_number VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE cart_product
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
)

-- INSERT INTO member(email, password, name, phone_number)
-- VALUES ('email1@test.com', '1234abcd!@', 'Test Name', '01012341234');
--
-- INSERT INTO member(email, password)
-- VALUES ('email2@test.com', '1234abcd!@');
--
-- INSERT INTO product(name, price, image_url)
-- VALUES ('자전거', 240000, 'https://mediahub.seoul.go.kr/uploads/mediahub/2022/04/jqPSSIsMrKiOfOZEvcRVkTCdhYrzBWuh.png');
--
-- INSERT INTO product(name, price, image_url)
-- VALUES ('물통', 10000, 'https://image.babosarang.co.kr/product/detail/E6G/2106161821325983/_600.jpg');
