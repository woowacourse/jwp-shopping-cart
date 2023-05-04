DELETE FROM member;

ALTER TABLE member ALTER COLUMN ID RESTART WITH 1;

INSERT INTO member(email, password)
VALUES ('test@test.com', 'test'),
       ('woowacourse@woowa.com', 'pobi');
