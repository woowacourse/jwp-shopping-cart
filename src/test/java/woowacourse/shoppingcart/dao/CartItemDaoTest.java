package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;

import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.shoppingcart.ProductInsertUtil;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private Long customerId;
    private Product product;

    private final CartItemDao cartItemDao;
    private final ProductInsertUtil productInsertUtil;
    private final CustomerDao customerDao;

    public CartItemDaoTest(DataSource dataSource) {
        cartItemDao = new CartItemDao(dataSource);
        productInsertUtil = new ProductInsertUtil(dataSource);
        customerDao = new CustomerDao(dataSource);
    }

    @BeforeEach
    void init() {
        customerId = customerDao.save(Customer.builder()
            .nickname("does")
            .email("asd@gmail.com")
            .password("1asd!")
            .build())
            .getId();
        Long productId = productInsertUtil.insert("banana", 1_000, "woowa1.com");
        product = new Product(productId, "banana", 1_000, "woowa1.com");
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다.")
    @Test
    void addCartItem() {
        // given
        CartItem cartItem = new CartItem(product, 2);

        // when
        CartItem saved = cartItemDao.save(customerId, cartItem);

        // then
        assertThat(saved.getId()).isNotNull();
    }

    // @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    // @Test
    // void findProductIdsByCustomerId() {
    //
    //     // given
    //     final Long customerId = 1L;
    //
    //     // when
    //     final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);
    //
    //     // then
    //     assertThat(productsIds).containsExactly(1L, 2L);
    // }
    //
    // @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    // @Test
    // void findIdsByCustomerId() {
    //
    //     // given
    //     final Long customerId = 1L;
    //
    //     // when
    //     final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
    //
    //     // then
    //     assertThat(cartIds).containsExactly(1L, 2L);
    // }
    //
    // @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    // @Test
    // void deleteCartItem() {
    //
    //     // given
    //     final Long cartId = 1L;
    //
    //     // when
    //     cartItemDao.deleteCartItem(cartId);
    //
    //     // then
    //     final Long customerId = 1L;
    //     final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);
    //
    //     assertThat(productIds).containsExactly(2L);
    // }
}
