package cart.dao;

import cart.entity.CreateItem;
import cart.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcItemDaoTest {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE item RESTART IDENTITY;");

        itemDao.save(new CreateItem("치킨", "a", 10000));
        itemDao.save(new CreateItem("피자", "b", 20000));
    }

    @Test
    @DisplayName("상품을 저장할 수 있다.")
    void save() {
        CreateItem item = new CreateItem("햄버거", "c", 2000);

        itemDao.save(item);

        List<Item> items = itemDao.findAll();

        assertAll(
                () -> assertThat(items.size()).isEqualTo(3),
                () -> assertThat(items.get(2).getId()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("상품 목록을 조회할 수 있다.")
    void findAll() {
        List<Item> items = itemDao.findAll();

        assertAll(
                () -> assertThat(items.size()).isEqualTo(2),
                () -> assertThat(items.get(1).getId()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("상품 정보를 수정할 수 있다.")
    void update() {
        Item item = new Item(2L, "햄버거", "c", 2000);

        itemDao.update(item);

        List<Item> items = itemDao.findAll();

        assertAll(
                () -> assertThat(items.size()).isEqualTo(2),
                () -> assertThat(items.get(1).getId()).isEqualTo(2),
                () -> assertThat(items.get(1).getName()).isEqualTo("햄버거"),
                () -> assertThat(items.get(1).getImageUrl()).isEqualTo("c"),
                () -> assertThat(items.get(1).getPrice()).isEqualTo(2000)
        );
    }

    @Test
    @DisplayName("상품 삭제 할 수 있다.")
    void delete() {
        itemDao.delete(2L);

        List<Item> items = itemDao.findAll();
        assertThat(items.size()).isEqualTo(1);
    }
}
