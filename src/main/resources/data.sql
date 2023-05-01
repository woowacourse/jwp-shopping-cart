CREATE TABLE IF NOT EXISTS product
(
    `id`        BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(25)   NOT NULL,
    `image_url` VARCHAR(500),
    `price`     INT           NOT NULL,
    `category`  VARCHAR(10)   NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS _user
(
    `id`       BIGINT UNIQUE NOT NULL AUTO_INCREMENT,
    `email`    VARCHAR(30)   NOT NULL,
    `password` VARCHAR(500),
    PRIMARY KEY (`id`)
);

INSERT INTO product(`name`, `image_url`, `price`, `category`) VALUES ('치킨',  'https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MDlfNjAg%2FMDAxNjgxMDA1NjI3OTMz.qOnKFKiwedtqn10zbps-4_RMwfjNa_HBsJ4SZOkuvKAg.jY1kM1nc6XpMWr7RTH-rZbVFL8fQXRjAnIBygcZ8OwQg.JPEG.nicebina%2FIMG_3773.jpg&type=sc960_832', 30000, 'KOREAN');
INSERT INTO product(`name`, `image_url`, `price`, `category`) VALUES ('초밥',  'https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MDhfMTg4%2FMDAxNjgwOTM0NjI0Njcw.Q8klXkday8sccZfn0gQXCh3sxUuQ6ZXD5K0f0A5taYAg.UWZ2PFD14rdv3JT9qq7a1eEn7aytKBBLlp0B3GM3Vikg.JPEG.macaron1229%2FIMG_3778.jpg&type=sc960_832', 50000, 'JAPANESE');
INSERT INTO product(`name`, `image_url`, `price`, `category`) VALUES ('파스타',  'https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MDRfMjg2%2FMDAxNjgwNjE0NjcxMzg2.YSdXockXSCYgIKkXfiUqCj-_7TOrnFjvJ27BUGH8p98g.CWye98JsGA_UGYbJA6-TzQbtRm3l5QMSBJI1-K9CvIUg.JPEG.honeydam%2FDSC_0409.JPG&type=sc960_832', 25000, 'WESTERN');


INSERT INTO _user(`email`, `password`) VALUES('a@a.com', 'password1');
INSERT INTO _user(`email`, `password`) VALUES('b@b.com', 'password2');