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
    email    VARCHAR(100)                   NOT NULL,
    password VARCHAR(255)                   NOT NULL,
    PRIMARY KEY (id)
);


insert into MEMBER(email, password)
values ('emailA@naver.com', 'passwordA'),
       ('emailB@kakao.com', 'passwordB');

