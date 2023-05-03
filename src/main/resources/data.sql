CREATE TABLE PRODUCT (
    id          INT           NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(50)   NOT NULL,
    image       VARCHAR(255)   NOT NULL,
    price       INT           NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Member (
    email       VARCHAR(255)   NOT NULL,
    password    VARCHAR(255)  NOT NULL,
    PRIMARY KEY (email)
);

insert into member(email, password) values('naver.com', '1234');
insert into member(email, password) values('google.com', '1234');
insert into member(email, password) values('nanan.com', '1234');
insert into member(email, password) values('nakakver.com', '1234');
