package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.global.DaoTest;
import woowacourse.shoppingcart.domain.Product;

public class CartItemDaoTest extends DaoTest {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItemDaoTest(DataSource dataSource,
                           NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        cartItemDao = new CartItemDao(dataSource, namedParameterJdbcTemplate);
        productDao = new ProductDao(dataSource, namedParameterJdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("banana", 1_000, "woowa1.com", 2_000));
        productDao.save(new Product("apple", 2_000, "woowa2.com", 3_000));

        cartItemDao.addCartItem(1L, 1L, 5L);
        cartItemDao.addCartItem(1L, 2L, 10L);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final long customerId = 1L;
        final long productId = 1L;
        final long count = 5L;

        // when
        final long cartId = cartItemDao.addCartItem(customerId, productId, count);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {

        // given
        final Long customerId = 1L;

        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);

        // then
        assertThat(productsIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {

        // given
        final Long customerId = 1L;

        // when
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        // then
        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void deleteCartItem() {

        // given
        final Long cartId = 1L;

        // when
        cartItemDao.deleteCartItemByProductId(cartId);

        // then
        final Long customerId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }
}
