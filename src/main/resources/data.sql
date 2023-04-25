-- TODO: 기능 구현에 필요한 내용을 추가하거나 수정하세요.
CREATE TABLE IF NOT EXISTS PRODUCT (
    id bigint NOT NULL AUTO_INCREMENT,
    name VARCHAR(8) NOT NULL,
    price int NOT NULL,
    image_url VARCHAR NOT NULL,
    PRIMARY KEY (id)
);
