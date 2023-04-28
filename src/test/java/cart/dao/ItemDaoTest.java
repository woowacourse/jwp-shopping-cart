package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.item.Item;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
        Item actual = itemDao.insert(createMacBookItem());

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getId()).isPositive(),
                () -> assertThat(actual.getName()).isEqualTo("맥북프로"),
                () -> assertThat(actual.getImageUrl()).isEqualTo("https://image.com"),
                () -> assertThat(actual.getPrice()).isEqualTo(10_000)
        );
    }

    @Nested
    @DisplayName("조회, 수정, 삭제 테스트")
    class RudTest {

        private Item savedItem;

        @BeforeEach
        void setUp() {
            savedItem = itemDao.insert(createMacBookItem());
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
        void deleteSuccess() {
            int deletedRecordCount = itemDao.delete(savedItem.getId());

            assertThat(deletedRecordCount).isOne();
        }

    }

    private Item createMacBookItem() {
        return new Item("맥북프로", "https://image.com", 10_000);
    }
}
