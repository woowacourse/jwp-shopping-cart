INSERT IGNORE INTO item(name, item_url, price) values ('치킨', 'https://image.homeplus.kr/td/f42afe4b-e0a8-4c79-a07c-aaee40c93a57', 10000);
INSERT IGNORE INTO item(name, item_url, price) values ('샐러드', 'https://www.raracost.com/images/sub/salad1.png', 20000);
INSERT IGNORE INTO item(name, item_url, price) values ('피자', 'https://www.ryupizza.com/wp-content/uploads/pizza401_1-1.jpg', 30000);
INSERT IGNORE INTO member(email, name, phone_number, password,cart) values ('admin@naver.com', 'test', '01098765432', 'qwer1234', '[{"id":1,"name":"치킨","imageUrl":"https://image.homeplus.kr/td/f42afe4b-e0a8-4c79-a07c-aaee40c93a57","price":10000},{"id":3,"name":"피자","imageUrl":"https://www.ryupizza.com/wp-content/uploads/pizza401_1-1.jpg","price":30000}]');
