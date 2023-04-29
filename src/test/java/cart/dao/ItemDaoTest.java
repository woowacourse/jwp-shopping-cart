package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.dto.ItemDto;
import cart.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ItemDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    ItemDao itemDao;

    @BeforeEach
    void setUp() {
        itemDao = new ItemDao(jdbcTemplate);
    }

    @Test
    @DisplayName("상품을 저장한다")
    void insertSuccess() {
        Item item = new Item("맥북", "image", 10000);

        Long savedId = itemDao.insert(item);

        assertThat(savedId).isNotNull();
    }

    @Test
    @DisplayName("지정된 id의 상품을 조회한다")
    void selectSuccess() {
        Item item = new Item("맥북", "image", 10000);
        Long savedId = itemDao.insert(item);

        ItemDto findItem = itemDao.findById(savedId).get();

        assertAll(
                () -> assertThat(findItem.getId()).isEqualTo(savedId),
                () -> assertThat(findItem.getName()).isEqualTo(item.getName()),
                () -> assertThat(findItem.getImageUrl()).isEqualTo(item.getImageUrl()),
                () -> assertThat(findItem.getPrice()).isEqualTo(item.getPrice())
        );
    }

    @Test
    @DisplayName("지정된 id의 상품을 변경한다")
    void updateSuccess() {
        Item originItem = new Item("맥북", "image", 10000);
        Long savedId = itemDao.insert(originItem);
        Item updateItem = new Item(originItem.getName(), originItem.getImageUrl(), 50000);

        itemDao.update(savedId, updateItem);
        ItemDto findItem = itemDao.findById(savedId).get();

        assertAll(
                () -> assertThat(findItem.getId()).isEqualTo(savedId),
                () -> assertThat(findItem.getName()).isEqualTo(originItem.getName()),
                () -> assertThat(findItem.getImageUrl()).isEqualTo(originItem.getImageUrl()),
                () -> assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice())
        );
    }

    @Test
    @DisplayName("지정된 id의 상품을 삭제한다")
    void deleteSuccess() {
        Item item = new Item("맥북", "image", 10000);
        Long savedId = itemDao.insert(item);

        int deletedCount = itemDao.delete(savedId);

        assertThat(deletedCount).isEqualTo(1);
    }
}
