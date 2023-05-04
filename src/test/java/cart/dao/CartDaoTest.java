package cart.dao;

import cart.domain.cart.CartProduct;
import cart.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
@Import(CartDao.class)
class CartDaoTest {

    private final User user = new User(1L, "hello@naver.com", "myPassword");

    @Autowired
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao.insert(user, 1L);
        cartDao.insert(user, 2L);
    }

    @DisplayName("유저의 카트에 정상적으로 상품을 추가할 수 있다.")
    @Test
    void insert() {
        assertDoesNotThrow(() -> cartDao.insert(user, 1L));
    }

    @DisplayName("유저의 카트에 담긴 상품들을 확인할 수 있다.")
    @Test
    void findAllByUser() {
        // given, when
        final List<CartProduct> cartProducts = cartDao.findAllByUser(user);

        // then
        assertAll(
                () -> assertThat(cartProducts.get(0).getProductId()).isEqualTo(1L),
                () -> assertThat(cartProducts.get(0).getProductNameValue()).isEqualTo("치킨"),
                () -> assertThat(cartProducts.get(1).getProductId()).isEqualTo(2L),
                () -> assertThat(cartProducts.get(1).getProductNameValue()).isEqualTo("초밥")
        );
    }

    @DisplayName("카트에 담긴 상품을 삭제할 수 있다.")
    @Test
    void delete() {
        // given
        final List<CartProduct> cartProducts = cartDao.findAllByUser(user);
        final int originalSize = cartProducts.size();

        // when
        cartDao.delete(user, 1L);
        final int updatedSize = cartDao.findAllByUser(user).size();

        // then
        assertThat(originalSize - 1).isEqualTo(updatedSize);
    }
}