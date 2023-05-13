INSERT INTO ITEM(name, image_url, price) VALUES ('자전거1', 'https://www.altonsports.com/prdimg/get/21-INNOZEN24_P_01%281060X600%29.jpg', 10000);
INSERT INTO ITEM(name, image_url, price) VALUES ('자전거2', 'https://cdn.imweb.me/thumbnail/20220817/7b35b82e7c1ce.jpg', 50000);
INSERT INTO ITEM(name, image_url, price) VALUES ('자전거3', 'https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2022/01/25/11/8/3294fc8a-92a5-410b-b3bb-fb3c7e18a1d9.jpg', 100000);

INSERT INTO MEMBER(email, password, name) VALUES ('aaa@aaa.com', 'helloSpring', 'gray');
INSERT INTO MEMBER(email, password, name) VALUES ('bbb@bbb.com', 'helloKotlin', 'zito');
INSERT INTO MEMBER(email, password, name) VALUES ('ccc@ccc.com', 'helloJava', 'jaemy');

INSERT INTO CART(member_id, item_id) VALUES ('1', '1');
INSERT INTO CART(member_id, item_id) VALUES ('1', '2');
INSERT INTO CART(member_id, item_id) VALUES ('2', '1');
