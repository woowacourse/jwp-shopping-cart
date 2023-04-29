package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Cart;
import cart.domain.item.Item;
import cart.domain.user.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("/carts.sql")
class CartDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다")
    void insertSuccess() {
        User user = new User(2L, "b@b.com", "b");
        Item item = new Item(1L, "자전거1", "https://image.com", 10000);

        Cart cart = cartDao.insert(user, item);

        assertThat(cart.getId()).isPositive();
    }

    @Nested
    @DisplayName("findByEmailAndItemId 테스트")
    class FindByEmailAndItemIdTest {

        @ParameterizedTest(name = "a@a.com 사용자의 장바구니에 존재하는 {0} 상품을 조회한다")
        @ValueSource(longs = {1L, 2L, 3L})
        void findByEmailAndItemIdSuccessWithNotEmptyCarts(Long itemId) {
            List<Cart> carts = cartDao.findByEmailAndItemId("a@a.com", itemId);

            assertThat(carts).isNotEmpty();
        }

        @ParameterizedTest(name = "b@b.com 사용자의 장바구니에 존재하지 않는 {0} 상품을 조회한다")
        @ValueSource(longs = {1L, 2L, 3L})
        void findByEmailAndItemIdSuccessWithEmptyCarts(Long itemId) {
            List<Cart> carts = cartDao.findByEmailAndItemId("b@b.com", itemId);

            assertThat(carts).isEmpty();
        }
    }

    @Nested
    @DisplayName("findAllByEmail 테스트")
    class FindAllByEmailTest {

        @Test
        @DisplayName("장바구니에 상품을 가지고 있는 a@a.com 사용자의 장바구니를 모두 조회한다")
        void findAllByEmailSuccessWithNotEmptyCarts() {
            List<Cart> carts = cartDao.findAllByEmail("a@a.com");

            assertThat(carts).hasSize(3);
        }

        @Test
        @DisplayName("장바구니에 상품을 가지고 있지 않은 b@b.com 사용자의 장바구니를 모두 조회한다")
        void findAllByEmailSuccessWithEmptyCarts() {
            List<Cart> carts = cartDao.findAllByEmail("b@b.com");

            assertThat(carts).isEmpty();
        }
    }

    @Test
    @DisplayName("장바구니의 상품을 삭제한다")
    void deleteSuccess() {
        int deleteRecordCount = cartDao.delete(1L);

        assertThat(deleteRecordCount).isOne();
    }
}
