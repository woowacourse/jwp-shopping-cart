package woowacourse.shoppingcart.infrastructure.jdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:init.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItemDaoTest(DataSource dataSource) {
        this.cartItemDao = new CartItemDao(dataSource);
        this.productDao = new ProductDao(dataSource);
    }

    @DisplayName("CartItem을 저장한다.")
    @Test
    void addCartItem() {
        Optional<Product> product = productDao.findProductById(1L);
        cartItemDao.save(new CartItem(1L, 1L, product.get(), 3));
    }

    @DisplayName("해당 고객의 CartItem 목록을 조회한다.")
    @Test
    void findCartItemsByCustomerId() {
        Optional<Product> 우유 = productDao.findProductById(1L);
        Optional<Product> 바나나 = productDao.findProductById(2L);
        CartItem cartItem1 = new CartItem(1L, 우유.get(), 3);
        CartItem cartItem2 = new CartItem(1L, 바나나.get(), 3);
        cartItemDao.save(cartItem1);
        cartItemDao.save(cartItem2);

        List<CartItem> cartItems = cartItemDao.findByCustomerId(1L);
        assertThat(cartItems).hasSize(2)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(List.of(cartItem1, cartItem2));
    }

    @DisplayName("CartItem들을 삭제한다.")
    @Test
    void deleteCartItems() {
        Optional<Product> 우유 = productDao.findProductById(1L);
        Optional<Product> 바나나 = productDao.findProductById(2L);
        CartItem cartItem1 = new CartItem(1L, 우유.get(), 3);
        CartItem cartItem2 = new CartItem(1L, 바나나.get(), 3);
        cartItemDao.save(cartItem1);
        cartItemDao.save(cartItem2);

        List<CartItem> cartItems = cartItemDao.findByCustomerId(1L);
        assertThat(cartItems.size()).isEqualTo(2);

        cartItemDao.deleteCartItemsByCustomerId(1L, List.of(1L, 2L));
        List<CartItem> deleteCartItems = cartItemDao.findByCustomerId(1L);
        assertThat(deleteCartItems.size()).isEqualTo(0);
    }
}
