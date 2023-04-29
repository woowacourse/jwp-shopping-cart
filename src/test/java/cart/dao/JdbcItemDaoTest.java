package cart.dao;

import cart.entity.CreateItem;
import cart.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql({"classpath:test_init.sql"})
class JdbcItemDaoTest {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        itemDao.save(new CreateItem("치킨", "a", 10000));
        itemDao.save(new CreateItem("피자", "b", 20000));
    }

    @Test
    @DisplayName("상품을 저장할 수 있다.")
    void saveSuccess() {
        CreateItem item = new CreateItem("햄버거", "c", 2000);

        itemDao.save(item);

        List<Item> items = itemDao.findAll();

        assertAll(
                () -> assertThat(items).hasSize(3),
                () -> assertThat(items.get(2).getId()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("상품 목록을 조회할 수 있다.")
    void findAllSuccess() {
        List<Item> items = itemDao.findAll();

        assertAll(
                () -> assertThat(items).hasSize(2),
                () -> assertThat(items.get(1).getId()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("상품 정보를 수정할 수 있다.")
    void updateSuccess() {
        CreateItem item = new CreateItem("햄버거", "c", 2000);

        int updateRow = itemDao.update(2L, item);

        List<Item> items = itemDao.findAll();
        Item expected = new Item(2L, "햄버거", "c", 2000);
        assertAll(
                () -> assertThat(updateRow).isEqualTo(1),
                () -> assertThat(items).hasSize(2),
                () -> assertThat(items.get(1))
                        .usingRecursiveComparison()
                        .isEqualTo(expected)
        );
    }

    @Test
    @DisplayName("없는 상품 정보를 수정하면 0이 반환된다.")
    void updateFail() {
        CreateItem item = new CreateItem("햄버거", "c", 2000);

        assertThat(itemDao.update(3L, item)).isZero();
    }

    @Test
    @DisplayName("상품 삭제 할 수 있다.")
    void deleteSuccess() {
        int deleteRow = itemDao.delete(2L);

        List<Item> items = itemDao.findAll();

        assertAll(
                () -> assertThat(deleteRow).isEqualTo(1),
                () -> assertThat(items).hasSize(1)
        );
    }

    @Test
    @DisplayName("없는 상품 삭제하면 0이 반환된다.")
    void deleteFail() {

        assertThat(itemDao.delete(3L)).isZero();
    }
}
