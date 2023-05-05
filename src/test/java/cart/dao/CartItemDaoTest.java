package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartItemDaoTest {

    private static final int PRODUCT_ID_ONE = 1;
    private static final int PRODUCT_ID_TWO = 2;
    private static final int PRODUCT_ID_THREE = 3;
    private static final int PRODUCT_ID_FOUR = 4;
    private static final int USER_ID = 1;
    private static final int OTHER_USER_ID = 999;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartItemDao cartItemDao;

    @BeforeEach
    void setUp() {
        this.cartItemDao = new CartItemDao(jdbcTemplate);

        cartItemDao.insertAll(USER_ID, List.of(PRODUCT_ID_ONE, PRODUCT_ID_TWO, PRODUCT_ID_THREE, PRODUCT_ID_FOUR));
    }

    @DisplayName("상품 한 개를 삽입한다")
    @Test
    void insert() {
        cartItemDao.insert(OTHER_USER_ID, PRODUCT_ID_ONE);

        assertThat(cartItemDao.selectAllItemsOf(OTHER_USER_ID))
                .extracting("productId")
                .containsExactly(PRODUCT_ID_ONE);
    }

    @DisplayName("상품 여러개를 삽입한다")
    @Test
    void insertAll() {
        assertThat(cartItemDao.selectAllItemsOf(USER_ID))
                .extracting("productId")
                .contains(PRODUCT_ID_ONE, PRODUCT_ID_TWO, PRODUCT_ID_THREE, PRODUCT_ID_FOUR);
    }

    @DisplayName("유저의 아이템 하나를 삭제한다")
    @Test
    void delete() {
        cartItemDao.delete(USER_ID, PRODUCT_ID_ONE);

        assertThat(cartItemDao.selectAllItemsOf(USER_ID))
                .extracting("productId")
                .doesNotContain(PRODUCT_ID_ONE);
    }

    @DisplayName("유저의 아이템들을 모두 제거한다")
    @Test
    void deleteAllItemsOfUserId() {
        cartItemDao.deleteAllItemsOf(USER_ID);

        assertThat(cartItemDao.selectAllItemsOf(USER_ID)).isEmpty();
    }

    @DisplayName("유저의 아이템들을 모두 꺼낸다")
    @Test
    void selectAllItemsOfUserId() {
        assertThat(cartItemDao.selectAllItemsOf(USER_ID))
                .extracting("productId")
                .containsExactlyInAnyOrder(PRODUCT_ID_ONE, PRODUCT_ID_TWO, PRODUCT_ID_THREE, PRODUCT_ID_FOUR);
    }

    @DisplayName("Id로 아이템을 꺼낸다")
    @Test
    void selectById() {
        assertThat(cartItemDao.selectBy(getGreatestId()))
                .extracting("productId")
                .isEqualTo(PRODUCT_ID_FOUR);
    }

    private Integer getGreatestId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM cart_item", Integer.class);
    }
}