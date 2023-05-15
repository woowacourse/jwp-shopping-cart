package cart.dao;

import cart.domain.cart.Cart;
import cart.persistance.dao.CartDao;
import cart.persistance.dao.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartDaoTest extends DaoTest {

    private final CartDao cartDao;

    @Autowired
    public CartDaoTest(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.cartDao = new CartDao(jdbcTemplate);
    }

    @DisplayName("1번 유저의 장바구니에 상품을 넣는다.")
    @Test
    void addProductToCartTest() {
        cartDao.addProduct(1, 1);
        cartDao.addProduct(1, 1);
        cartDao.addProduct(1, 1);

        final Cart cart = cartDao.findByUserId(1);

        assertThat(cart.getCartItems()).hasSize(5);
    }

    @DisplayName("1번 유저의 장바구니를 모두 조회한다.")
    @Test
    void findAllOfProductForMember1Test() {
        final Cart cart = cartDao.findByUserId(1);

        assertThat(cart.getCartItems()).hasSize(2);
    }

    @DisplayName("1번 유저의 장바구니 상품을 지운다.")
    @Test
    void deleteProductFromCart() {
        cartDao.removeById(1, 1);

        final Cart cart = cartDao.findByUserId(1);

        assertThat(cart.getCartItems()).hasSize(1);
    }

    @DisplayName("삭제할 product가 없을 시 예외가 발생한다")
    @Test
    void deleteProductException() {
        cartDao.removeById(1, 1);
        assertThatThrownBy(() -> cartDao.removeById(1, 1))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("자신의 상품이 아닌 것을 삭제할 수 없다.")
    @Test
    void deleteProductDoNotHaveException() {
        assertThatThrownBy(() -> cartDao.removeById(1, 2))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
