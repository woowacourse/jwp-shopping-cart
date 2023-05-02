-- TODO: 기능 구현에 필요한 내용을 추가하거나 수정하세요.
CREATE TABLE IF NOT EXISTS PRODUCT (
    id bigint NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    price int NOT NULL,
    image_url VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `USER` (
    id bigint NOT NULL AUTO_INCREMENT,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO `USER` (email, password) VALUES ('aaa1234@google.com', 'aaaa');
INSERT INTO `USER` (email, password) VALUES ('bbb1234@google.com', 'bbbbb');
