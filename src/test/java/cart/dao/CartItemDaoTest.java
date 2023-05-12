package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.entity.CartItemEntity;
import cart.entity.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CartItemDaoTest {

    private final CartItemEntity cartItemEntity = new CartItemEntity(99, 100);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartItemDao cartItemDao;

    @BeforeEach
    void setUp() {
        cartItemDao = new CartItemDao(jdbcTemplate);
    }

    @DisplayName("addCartItem 성공 테스트")
    @Test
    void addCartItem() {
        assertDoesNotThrow(() -> cartItemDao.addCartItem(cartItemEntity));
    }

    @DisplayName("isCartItem 성공 테스트")
    @Test
    void isCartItemExist() {
        cartItemDao.addCartItem(cartItemEntity);

        assertThat(cartItemDao.isCartItemExist(99, 100)).isTrue();
    }

    @DisplayName("isCartItem 실패 테스트")
    @Test
    void failIsCartItemExist() {
        cartItemDao.addCartItem(cartItemEntity);

        assertAll(
                () -> assertThat(cartItemDao.isCartItemExist(100, 100)).isFalse(),
                () -> assertThat(cartItemDao.isCartItemExist(99, 101)).isFalse(),
                () -> assertThat(cartItemDao.isCartItemExist(100, 101)).isFalse()
        );
    }

    @DisplayName("selectAllCartItems 성공 테스트")
    @Test
    void selectAllCartItems() {
        List<ProductEntity> productEntities = cartItemDao.selectAllCartItems(1);

        assertAll(
                () -> assertThat(productEntities).hasSize(2),
                () -> assertThat(productEntities).extracting("name", "price")
                        .contains(
                                tuple("케로로", 10000),
                                tuple("기로로", 20000)
                        )
        );
    }

    @DisplayName("removeCartItem 성공 테스트")
    @Test
    void removeCartItem() {
        int cartId = cartItemDao.addCartItem(cartItemEntity);

        cartItemDao.deleteCartItem(cartId);

        assertThat(cartItemDao.isCartItemExist(99, 100)).isFalse();
    }
}
