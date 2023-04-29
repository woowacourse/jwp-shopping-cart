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
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
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

        private Cart cart;

        @BeforeEach
        void setUp() {
            User user = new User(1L, "a@a.com", "a");
            Item item = new Item(5L, "자전거1", "https://image.com", 10000);

            cart = cartDao.insert(user, item);
        }

        @Test
        @DisplayName("장바구니에 상품이 존재하는 사용자의 상품을 조회한다")
        void findByEmailAndItemIdSuccessWithNotEmptyCarts() {
            List<Cart> carts = cartDao.findByEmailAndItemId("a@a.com", cart.getItem().getId());

            assertThat(carts).isNotEmpty();
        }

        @Test
        @DisplayName("장바구니에 상품이 존재하지 않는 사용자의 상품을 조회한다")
        void findByEmailAndItemIdSuccessWithEmptyCarts() {
            List<Cart> carts = cartDao.findByEmailAndItemId("b@b.com", cart.getItem().getId());

            assertThat(carts).isEmpty();
        }
    }

    @Nested
    @DisplayName("findAllByEmail 테스트")
    class FindAllByEmailTest {

        @BeforeEach
        void setUp() {
            User user = new User(1L, "a@a.com", "a");
            Item item = new Item(1L, "자전거1", "https://image.com", 10000);

            cartDao.insert(user, item);
        }

        @Test
        @DisplayName("장바구니에 상품을 가지고 있는 a@a.com 사용자의 장바구니를 모두 조회한다")
        void findAllByEmailSuccessWithNotEmptyCarts() {
            List<Cart> carts = cartDao.findAllByEmail("a@a.com");

            assertThat(carts).isNotEmpty();
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
        User user = new User(2L, "b@b.com", "b");
        Item item = new Item(3L, "자전거3", "https://image.com", 10000);
        Cart cart = cartDao.insert(user, item);

        int deleteRecordCount = cartDao.delete(cart.getId(), cart.getUser().getEmail());

        assertThat(deleteRecordCount).isOne();
    }

    @ParameterizedTest(name = "사용자 이메일 {0}과 상품 ID {1}이 주어지면 어떠한 상품도 삭제되지 않는다")
    @DisplayName("장바구니에 삭제할 상품이 존재하지 않으면 예외가 발생한다")
    @CsvSource(value = {"5:a@a.com", "1:c@c.com"}, delimiter = ':')
    void deleteFailWithInvalidEmailOrItemId(Long cartId, String email) {
        int deleteRecordCount = cartDao.delete(cartId, email);

        assertThat(deleteRecordCount).isZero();
    }
}
