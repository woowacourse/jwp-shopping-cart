package cart.dao;

import cart.entity.CreateItem;
import cart.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static cart.Pixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql({"classpath:test_init.sql"})
class JdbcItemDaoTest {

    @Autowired
    private ItemDao itemDao;

    @BeforeEach
    void setUp() {
        itemDao.save(CREATE_ITEM1);
        itemDao.save(CREATE_ITEM2);
    }

    @Test
    @DisplayName("상품을 저장할 수 있다.")
    void saveSuccess() {
        CreateItem item = CREATE_ITEM3;

        itemDao.save(item);

        List<Item> items = itemDao.findAll();

        assertAll(
                () -> assertThat(items).hasSize(3),
                () -> assertThat(items.get(2))
                        .usingRecursiveComparison()
                        .isEqualTo(ITEM3)
        );
    }

    @Test
    @DisplayName("상품 목록을 조회할 수 있다.")
    void findAllSuccess() {
        List<Item> items = itemDao.findAll();

        assertAll(
                () -> assertThat(items).hasSize(2),
                () -> assertThat(items.get(1))
                        .usingRecursiveComparison()
                        .isEqualTo(ITEM2),
                () -> assertThat(items.get(0))
                        .usingRecursiveComparison()
                        .isEqualTo(ITEM1)
        );
    }

    @Test
    @DisplayName("상품 정보를 수정할 수 있다.")
    void updateSuccess() {
        CreateItem item = CREATE_ITEM3;

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
        CreateItem item = CREATE_ITEM3;

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
