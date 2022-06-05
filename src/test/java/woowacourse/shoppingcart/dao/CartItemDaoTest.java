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
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
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
//
//    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
//    @Test
//    void findProductIdsByCustomerId() {
//
//        // given
//        final Long customerId = 1L;
//
//        // when
//        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);
//
//        // then
//        assertThat(productsIds).containsExactly(1L, 2L);
//    }
//
//
//    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
//    @Test
//    void deleteCartItem() {
//
//        // given
//        final Long cartId = 1L;
//
//        // when
//        cartItemDao.deleteCartItem(cartId);
//
//        // then
//        final Long customerId = 1L;
//        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);
//
//        assertThat(productIds).containsExactly(2L);
//    }
}
