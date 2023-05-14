CREATE TABLE product
(
    id    INT           NOT NULL AUTO_INCREMENT,
    name  VARCHAR(50)   NOT NULL,
    price INT           NOT NULL,
    image VARCHAR(2000) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id       INT          NOT NULL AUTO_INCREMENT,
    email    VARCHAR(320) NOT NULL,
    password VARCHAR(20)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id         INT NOT NULL AUTO_INCREMENT,
    member_id  INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

insert into member (email, password)
values ('huchu@woowahan.com', '1234567a!');
insert into member (email, password)
values ('gavi@woowahan.com', '1234567a!');
insert into member (email, password)
values ('merry@woowahan.com', '1234567a!');

insert into product (name, price, image)
values ('huchu', 1000,
        'https://sitem.ssgcdn.com/17/97/60/item/0000006609717_i1_1100.jpg');
insert into product (name, price, image)
values ('gavi', 10000,
        'https://cdn.spotvnews.co.kr/news/photo/202211/566369_791169_1130.jpg');
insert into product (name, price, image)
values ('merry', 20000,
        'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcKaUwJ%2FbtqPen616ZX%2FYndkfJ3216Z9zVZpgurdV0%2Fimg.png');
