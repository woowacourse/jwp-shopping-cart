CREATE TABLE PRODUCT (
    id          INT           NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(50)   NOT NULL,
    image       VARCHAR(255)   NOT NULL,
    price       INT           NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE MEMBER (
    id          INT           NOT NULL AUTO_INCREMENT,
    email       VARCHAR(255)  NOT NULL,
    password    VARCHAR(255)  NOT NULL,
    PRIMARY KEY (email),
    UNIQUE KEY unique_email (email)
);

insert into PRODUCT(`name`, image, price) values ('item1', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5c6VkPCiNvUmomb-iGTLqP76uu9FOsJWRpg&usqp=CAU', 1000);
insert into PRODUCT(`name`, image, price) values ('item2', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5c6VkPCiNvUmomb-iGTLqP76uu9FOsJWRpg&usqp=CAU', 2000);
insert into PRODUCT(`name`, image, price) values ('item3', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5c6VkPCiNvUmomb-iGTLqP76uu9FOsJWRpg&usqp=CAU', 3000);

insert into MEMBER(email, password) values('naver.com', '1234');
insert into MEMBER(email, password) values('google.com', '1234');
insert into MEMBER(email, password) values('nanan.com', '1234');
insert into MEMBER(email, password) values('nakakver.com', '1234');
