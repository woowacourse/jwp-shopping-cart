SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE CART;
TRUNCATE TABLE PRODUCTS;
TRUNCATE TABLE MEMBERS;
SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO PRODUCTS (id, name, price, image) VALUES (1, '치킨', 1000, 'https://i.namu.wiki/i/pTVoWDp5G09PGTRUTbCy8raXo9CB47uF2wcuzdUYTlPwRjU6zjl0Reoih4MIXXRTnfxVl-yKlPjTQSVhAbfSxA.webp');
INSERT INTO PRODUCTS (id, name, price, image) VALUES (2, '피자', 2000, 'https://cdn.dominos.co.kr/admin/upload/goods/20200311_x8StB1t3.jpg');
INSERT INTO PRODUCTS (id, name, price, image) VALUES (3, '떡볶이', 3000, 'https://static.wtable.co.kr/image/production/service/recipe/1661/e8987193-0529-44c8-9740-56c0352f7184.jpg?size=1024x1024');

INSERT INTO MEMBERS (id, email, password) VALUES (1, 'yuja@naver.com', 'yuja1234');
INSERT INTO MEMBERS (id, email, password) VALUES (2, 'mint@google.com', 'min567');
INSERT INTO MEMBERS (id, email, password) VALUES (3, 'tea@kakao.com', 'tea890');
