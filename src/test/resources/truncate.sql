SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE orders_detail;
TRUNCATE TABLE cart_item;
TRUNCATE TABLE orders;
TRUNCATE TABLE image;
TRUNCATE TABLE product;
TRUNCATE TABLE customer;
SET FOREIGN_KEY_CHECKS = 1;

insert into customer (email, password, username)
values ('puterism@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4','puterism'),
       ('tanney-102@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'tanney-102'),
       ('jho2301@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'jho2301'),
       ('365kim@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', '365kim'),
       ('dudtjr913@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'dudtjr913'),
       ('jum0@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'jum0'),
       ('hyunjukim@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'hyunjukim'),
       ('zereight@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'zereight'),
       ('devhyun637@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'devhyun637'),
       ('swon3210@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'swon3210'),
       ('bigsaigon3@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'bigsaigon3'),
       ('yungo1846@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'yungo1846'),
       ('zigsong@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'zigsong'),
       ('iborymagic@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'iborymagic'),
       ('0307kwon@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', '0307kwon'),
       ('gwangyeol@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'gwangyeol'),
       ('shinsehan@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'shinsehan'),
       ('ddongule@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'ddongule'),
       ('seojihwan@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'seojihwan'),
       ('0imbean0@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', '0imbean0'),
       ('youngkwon@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'youngkwon'),
       ('hchayan@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'hchayan'),
       ('2sooy@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', '2sooy'),
       ('yujo11@email.com', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'yujo11'),
       ('sunhpark4@email.com2', '65c21921ca10a8502757efc9aa552874d181c6206feb2845a921eb57f5e518d4', 'sunhpark42')
;

