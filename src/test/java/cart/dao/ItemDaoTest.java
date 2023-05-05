package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.item.Item;
import cart.repository.dao.ItemDao;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ItemDaoTest {

    ItemDao itemDao;
    Item savedItem;

    @BeforeEach
    void setUp(@Autowired JdbcTemplate jdbcTemplate) {
        itemDao = new ItemDao(jdbcTemplate);
        savedItem = itemDao.insert(createMacBookItem());
    }

    @Test
    @DisplayName("상품을 저장한다")
    void insertSuccess() {
        Item actual = itemDao.insert(createMacBookItem());

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getId()).isPositive(),
                () -> assertThat(actual.getName()).isEqualTo("맥북프로"),
                () -> assertThat(actual.getImageUrl()).isEqualTo("https://image.com"),
                () -> assertThat(actual.getPrice()).isEqualTo(10_000)
        );
    }

    @Test
    @DisplayName("지정된 id의 상품을 조회한다")
    void selectSuccess() {
        Optional<Item> actual = itemDao.findById(savedItem.getId());

        assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get().getName()).isEqualTo("맥북프로"),
                () -> assertThat(actual.get().getImageUrl()).isEqualTo("https://image.com"),
                () -> assertThat(actual.get().getPrice()).isEqualTo(10_000)
        );
    }

    @Test
    @DisplayName("지정된 id의 상품을 변경한다")
    void updateSuccess() {
        Item updateItem = new Item(savedItem.getId(), "맥북", "https://image.net", 50_000);

        int updateRecordCount = itemDao.update(updateItem);

        assertThat(updateRecordCount).isOne();
    }

    @Test
    @DisplayName("지정된 id의 상품을 삭제한다")
    void deleteByIdSuccess() {
        int deletedRecordCount = itemDao.deleteById(savedItem.getId());

        assertThat(deletedRecordCount).isOne();
    }

    private Item createMacBookItem() {
        return new Item("맥북프로", "https://image.com", 10_000);
    }
}
