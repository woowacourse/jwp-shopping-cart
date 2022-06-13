package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_2;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.entity.CartItemEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        cartItemDao = new JdbcCartItemDao(jdbcTemplate);
        productDao = new JdbcProductDao(jdbcTemplate);
        this.customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다.")
    @Test
    void addCartItem() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId = productDao.save(PRODUCT_1);
        final int quantity = 3;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId, quantity);

        // then
        assertThat(cartId).isPositive();
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long productId2 = productDao.save(PRODUCT_2);
        final Long cartId1 = cartItemDao.addCartItem(customerId, productId1, 1);
        final Long cartId2 = cartItemDao.addCartItem(customerId, productId2, 2);

        // when
        List<CartItemEntity> cartItemEntities = cartItemDao.findCartByCustomerId(customerId);

        // then
        assertThat(cartItemEntities)
                .extracting("id", "customerId", "productId", "quantity")
                .containsExactly(
                        tuple(cartId1, customerId, productId1, 1),
                        tuple(cartId2, customerId, productId2, 2)
                );
    }

    @DisplayName("cartId 와 customerId에 해당하는 장바구니 아이템이 존재하는 지 확인한다.")
    @Test
    public void hasCartItem() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long cartId1 = cartItemDao.addCartItem(customerId, productId1, 1);

        // when
        final boolean result = cartItemDao.hasCartItem(cartId1, customerId);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("cartId와 customerId에 해당하는 장바구니 아이템이 존재하지 않는 지 확인한다.")
    @Test
    public void hasNotCartItem() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long cartId1 = cartItemDao.addCartItem(customerId, productId1, 1);

        // when
        final boolean result = cartItemDao.hasCartItem(cartId1 + 1, customerId);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("cartId에 해당하는 장바구니 아이템을 삭제한다.")
    @Test
    public void deleteCartItem() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long cartId1 = cartItemDao.addCartItem(customerId, productId1, 1);

        // when
        cartItemDao.deleteCartItem(cartId1);
        final boolean result = cartItemDao.hasCartItem(cartId1, customerId);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("장바구니에 해당 상품이 존재할 때 true를 반환한다.")
    @Test
    public void hasProduct() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        cartItemDao.addCartItem(customerId, productId1, 1);

        // when
        boolean result = cartItemDao.hasProduct(customerId, productId1);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("장바구니에 해당 상품이 존재하지 않을 때 false 반환한다.")
    @Test
    public void hasNotProduct() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        cartItemDao.addCartItem(customerId, productId1, 1);

        // when
        boolean result = cartItemDao.hasProduct(customerId, productId1 + 1);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("장바구니 아이템을 수정한다.")
    @Test
    public void updateCartItem() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long cartItemId = cartItemDao.addCartItem(customerId, productId1, 1);

        // when
        cartItemDao.updateCartItem(cartItemId, customerId, productId1, 3);

        final List<CartItemEntity> cartItemEntities = cartItemDao.findCartByCustomerId(customerId);

        // then
        assertThat(cartItemEntities).extracting("id", "customerId", "productId", "quantity")
                .containsExactly(tuple(cartItemId, customerId, productId1, 3));
    }
}
