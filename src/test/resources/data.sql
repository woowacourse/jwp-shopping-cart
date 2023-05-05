SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE members;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO members (email, password)
VALUES ('dummy@gmail.com', 'abcd1234');
INSERT INTO members (email, password)
VALUES ('dummy2@gmail.com', 'abcd5678');

INSERT INTO products (name, image_url, price)
VALUES ('dummy', 'https://ifh.cc/g/bQpAgl.jpg', 10000);
INSERT INTO products (name, image_url, price)
VALUES ('dummy2', 'https://ifh.cc/g/WldLmN.jpg', 20000);
