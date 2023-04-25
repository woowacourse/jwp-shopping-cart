package cart.dao;

import cart.dao.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ItemDaoTest {

    private final ItemDao itemDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ItemDaoTest(final JdbcTemplate jdbcTemplate) {
        this.itemDao = new ItemDao(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @DisplayName("아이템의 전체 목록을 조회한다")
    @Test
    void findAll() {
        // given
        final String truncateSql = "TRUNCATE TABLE items ";
        jdbcTemplate.update(truncateSql);
        final String insertSql = "INSERT INTO items (name, image_url, price) values ('위키드', 'https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg', 150000);\n " +
                "INSERT INTO items (name, image_url, price) values ('마틸다', 'https://ticketimage.interpark.com/Play/image/large/22/22009226_p.gif', 100000);\n" +
                "INSERT INTO items (name, image_url, price) values ('빌리 엘리어트', 'https://t1.daumcdn.net/cfile/226F4D4C544F42CF34', 200000);";
        jdbcTemplate.update(insertSql);

        // when
        List<Item> items = itemDao.findAll();

        // then
        assertThat(items).hasSize(3);
    }
}
