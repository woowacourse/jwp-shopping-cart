package cart.dao;

import cart.entity.CreateItem;
import cart.entity.Item;
import cart.exception.ServiceIllegalArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static cart.Pixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @DisplayName("상품을 저장할 수 있다.")
    @Test
    void save_success() {
        itemDao.save(CREATE_ITEM3);

        List<Item> items = itemDao.findAll();

        assertAll(
                () -> assertThat(items).hasSize(3),
                () -> assertThat(items.get(2))
                        .usingRecursiveComparison()
                        .isEqualTo(ITEM3)
        );
    }

    @DisplayName("이미 상품이 있는 경우 상품을 저장할 수 없다.")
    @Test
    void save_fail() {
        assertThatThrownBy(() -> itemDao.save(CREATE_ITEM1))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessage("이미 동일한 상품이 존재합니다.");
    }

    @DisplayName("상품 목록을 조회할 수 있다.")
    @Test
    void findAll_success() {
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

    @DisplayName("상품을 조회할 수 있다.")
    @Test
    void findById_success() {
        Item item = itemDao.findById(1L);

        assertThat(item)
                .usingRecursiveComparison()
                .isEqualTo(ITEM1);
    }

    @DisplayName("상품 번호가 없는 경우 조회할 수 없다.")
    @Test
    void findById_fail() {
        assertThat(itemDao.findById(3L)).isNull();
    }

    @DisplayName("상품 정보를 수정할 수 있다.")
    @Test
    void update_success() {
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

    @DisplayName("없는 상품 정보를 수정하면 0이 반환된다.")
    @Test
    void update_fail() {
        CreateItem item = CREATE_ITEM3;

        assertThat(itemDao.update(3L, item)).isZero();
    }

    @DisplayName("상품 삭제 할 수 있다.")
    @Test
    void delete_success() {
        int deleteRow = itemDao.delete(2L);

        List<Item> items = itemDao.findAll();

        assertAll(
                () -> assertThat(deleteRow).isEqualTo(1),
                () -> assertThat(items).hasSize(1)
        );
    }

    @DisplayName("없는 상품 삭제하면 0이 반환된다.")
    @Test
    void delete_fail() {

        assertThat(itemDao.delete(3L)).isZero();
    }


    @DisplayName("id를 기준으로 상품이 있다면 true가 반환된다.")
    @Test
    void isItemExistsById_true() {

        assertThat(itemDao.isItemExistsById(2L)).isTrue();
    }

    @DisplayName("id를 기준으로 상품이 없다면 false가 반환된다.")
    @Test
    void isItemExistsById_false() {

        assertThat(itemDao.isItemExistsById(3L)).isFalse();
    }

    @DisplayName("CreateItem의 값을 기준으로 상품이 있다면 true가 반환된다.")
    @Test
    void isItemExistsByCreateItem_true() {

        assertThat(itemDao.isItemExistsByCreateItem(CREATE_ITEM1)).isTrue();
    }

    @DisplayName("CreateItem의 값을 기준으로 상품이 없다면 false가 반환된다.")
    @Test
    void isItemExistsByCreateItem_false() {

        assertThat(itemDao.isItemExistsByCreateItem(CREATE_ITEM3)).isFalse();
    }
}
