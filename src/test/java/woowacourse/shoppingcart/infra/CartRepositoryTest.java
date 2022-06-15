//package woowacourse.shoppingcart.infra;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.TestConstructor;
//import org.springframework.test.context.jdbc.Sql;
//import woowacourse.shoppingcart.domain.Product;
//import woowacourse.shoppingcart.infra.dao.CartDao;
//import woowacourse.shoppingcart.infra.dao.JdbcCartDao;
//import woowacourse.shoppingcart.infra.dao.ProductDao;
//import woowacourse.shoppingcart.infra.dao.entity.CartEntity;
//import woowacourse.shoppingcart.infra.dao.entity.ProductEntity;
//
//@JdbcTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Sql({"/truncate.sql", "/auth.sql", "/product.sql"})
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//public class CartRepositoryTest {
//    private final CartDao cartDao;
//    private final JdbcTemplate jdbcTemplate;
//
//    public CartRepositoryTest(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.cartDao = new JdbcCartDao(jdbcTemplate);
//    }
//
//    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다.")
//    @Test
//    void addCartItem() {
//        // given
//        final Long customerId = 1L;
//        final Long productId = 1L;
//        new CartEntity(null, customerId, new ProductEntity())
//
//        // when
//        final Long cartId = cartDao.save(customerId, productId);
//
//        // then
//        assertThat(cartId).isEqualTo(3L);
//    }
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
//    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
//    @Test
//    void findIdsByCustomerId() {
//
//        // given
//        final Long customerId = 1L;
//
//        // when
//        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
//
//        // then
//        assertThat(cartIds).containsExactly(1L, 2L);
//    }
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
//}
