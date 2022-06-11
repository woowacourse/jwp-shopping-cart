package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartItemDaoTest(DataSource dataSource) {
        cartItemDao = new CartItemDao(dataSource);
        customerDao = new CustomerDao(dataSource);
        productDao = new ProductDao(dataSource);
    }

    @BeforeEach
    void setUp() {
        customerDao.save(new Customer("email@email.com", "password123@A", "zero"));
        productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productDao.save(new Product("apple", 1_000, "woowa2.com"));
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        final int quantity = 3;

        // when
        final Long cartId = cartItemDao.save(customerId, productId, quantity);

        // then
        assertThat(cartId).isEqualTo(1L);
    }

    @DisplayName("Id를 통해 장바구니를 조회할 수 있다.")
    @Test
    void findCartItem() {
        // given
        cartItemDao.save(1L, 1L, 3);

        // when
        Cart cart = cartItemDao.findById(1L).get();

        // then
        assertThat(cart).usingRecursiveComparison()
                .isEqualTo(new Cart(1L, 1L, 1L, 3));
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {
        // given
        cartItemDao.save(1L, 1L, 1);
        cartItemDao.save(1L, 2L, 1);

        // when
        final List<Cart> carts = cartItemDao.findAllByCustomerId(1L).get();

        // then
        assertThat(carts).usingRecursiveComparison()
                .isEqualTo(List.of(
                        new Cart(1L, 1L, 1L, 1),
                        new Cart(2L, 1L, 2L, 1)
                ));
    }

    @DisplayName("상품 수량을 변경할 수 있다.")
    @Test
    void updateQuantityByProductId() {
        // given
        cartItemDao.save(1L, 1L, 1);

        // when
        cartItemDao.updateQuantity(1L, 1L, 2);
        Cart cart = cartItemDao.findById(1L).get();

        // then
        assertThat(cart).usingRecursiveComparison()
                .isEqualTo(new Cart(1L, 1L, 1L, 2));
    }

    @DisplayName("상품을 삭제할 수 있다.")
    @Test
    void findProductIdsByCustomerId() {
        // given
        cartItemDao.save(1L, 1L, 1);

        // when
        cartItemDao.deleteByCustomerIdAndProductId(1L, 1L);

        // then
        assertThat(cartItemDao.findById(1L)).isEmpty();
    }
}
