CREATE TABLE product
(
    id    BIGINT       NOT NULL AUTO_INCREMENT,
    name  VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    price INT          NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO PRODUCT (name,image,price) VALUES('치킨', 'https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/d440b8f4-91c3-4272-8a81-876e9aaffb9c/RisingStarGraphBox.jpg', 1000);