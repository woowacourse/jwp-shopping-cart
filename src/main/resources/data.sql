-- TODO: 기능 구현에 필요한 내용을 추가하거나 수정하세요.
CREATE TABLE IF NOT EXISTS PRODUCT (
    id bigint NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    price int NOT NULL,
    image_url VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MEMBER (
    id bigint NOT NULL AUTO_INCREMENT,
    email VARCHAR(32) NOT NULL,
    password VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS CART(
=======
CREATE TABLE CART(
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
=======
CREATE TABLE IF NOT EXISTS CART(
>>>>>>> 58261c83 (refactor: fk까지 속성이 전이되도록 변경)
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
<<<<<<< HEAD
<<<<<<< HEAD
    FOREIGN KEY (member_id) REFERENCES MEMBER(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES PRODUCT(id) ON DELETE CASCADE
);

=======
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
=======
    FOREIGN KEY (member_id) REFERENCES MEMBER(id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT(id)
=======
    FOREIGN KEY (member_id) REFERENCES MEMBER(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES PRODUCT(id) ON DELETE CASCADE
>>>>>>> 58261c83 (refactor: fk까지 속성이 전이되도록 변경)
);

<<<<<<< HEAD
-- INSERT INTO PRODUCT(name, price, image_url) values('맨유', 9999909, 'man united');

<<<<<<< HEAD
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
INSERT INTO MEMBER(email, password) values('a@a.com', 'password1');
INSERT INTO MEMBER(email, password) values('b@b.com', 'password2');
=======
-- INSERT INTO MEMBER(email, password) values('a@a.com', 'password1');
-- INSERT INTO MEMBER(email, password) values('b@b.com', 'password2');
>>>>>>> 28a6d971 (feat: findAllByMemberId 구현)
=======
INSERT INTO MEMBER(email, password) values('a@a.com', 'password1');
INSERT INTO MEMBER(email, password) values('b@b.com', 'password2');
>>>>>>> 339fefaa (feat: findAllByMemberId 테스트 및 테스트 전용 sql 파일 설정)
