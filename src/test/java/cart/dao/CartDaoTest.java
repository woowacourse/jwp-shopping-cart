package cart.dao;

import cart.persistance.dao.CartDao;
import cart.persistance.dao.exception.ProductNotFoundException;
import cart.persistance.entity.CartProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Sql("/test.sql")
@JdbcTest
class CartDaoTest {

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

        final List<CartProductEntity> products = cartDao.findByUserId(1);

        assertThat(products).hasSize(5);
    }

    @DisplayName("1번 유저의 장바구니를 모두 조회한다.")
    @Test
    void findAllOfProductForMember1Test() {
        final List<CartProductEntity> products = cartDao.findByUserId(1);

        assertThat(products).hasSize(2);
    }

    @DisplayName("1번 유저의 장바구니 상품을 지운다.")
    @Test
    void deleteProductFromCart() {
        cartDao.removeFromCartById(1);

        final List<CartProductEntity> products = cartDao.findByUserId(1);

        assertThat(products).hasSize(1);
    }

    @DisplayName("삭제할 product가 없을 시 예외가 발생한다")
    @Test
    void deleteProductException() {
        cartDao.removeFromCartById(1);
        assertThatThrownBy(() -> cartDao.removeFromCartById(1))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
