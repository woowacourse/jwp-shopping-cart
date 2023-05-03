set referential_integrity false;
truncate table cart_table restart identity;
truncate table user_table restart identity;
truncate table products_table restart identity;
set referential_integrity true;
