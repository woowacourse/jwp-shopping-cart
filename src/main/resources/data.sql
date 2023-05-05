insert into user_table(user_email,user_password) values ('aaaa@aaaa.com','password');
insert into user_table(user_email,user_password) values ('bbbb@bbbbb.com','password');

insert into products_table(product_name,product_price,product_image) values('test1',1000,'https://pbs.twimg.com/profile_images/1374979417915547648/vKspl9Et_400x400.jpg');
insert into products_table(product_name,product_price,product_image) values('test2',1000,'https://pbs.twimg.com/profile_images/1374979417915547648/vKspl9Et_400x400.jpg');

insert into cart_table(user_id,product_id) values(1,1);
insert into cart_table(user_id,product_id) values(1,2);
