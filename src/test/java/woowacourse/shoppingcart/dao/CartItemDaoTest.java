package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productDao.save(new Product("apple", 2_000, "woowa2.com"));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)", 1L, 1L);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)", 1L, 2L);
    }

    @Test
    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    void save() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        final Long cartId = cartItemDao.save(customerId, productId);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @Test
    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    void findProductIdsByCustomerId() {
        // given
        final Long customerId = 1L;

        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);

        // then
        assertThat(productsIds).containsExactly(1L, 2L);
    }

    @Test
    @DisplayName("고객 아이디를 넣으면, 고객이 장바구니에 담은 상품들을 가져온다.")
    void findProductsByCustomerId() {
        //given
        final Long customerId = 1L;

        //when
        final List<Cart> actual = cartItemDao.findCartItemsByCustomerId(customerId);

        //then
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    void findIdsByCustomerId() {
        // given
        final Long customerId = 1L;

        // when
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        // then
        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @Test
    @DisplayName("장바구니 상품 수량을 수정한다.")
    void updateQuantity() {
        //given
        final Long customerId = 1L;
        final long cartItemId = 1L;

        //when
        cartItemDao.updateQuantity(cartItemId, 10);

        //then
        assertThatCode(() -> cartItemDao.findCartItemsByCustomerId(customerId))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("해당 장바구니의 상품을 1개 삭제한다.")
    void deleteCartItem() {
        // given
        final Long cartId = 1L;

        // when
        cartItemDao.deleteCartItem(cartId);

        // then
        final Long customerId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }

    @Test
    @DisplayName("해당 장바구니의 상품들을 삭제한다.")
    void deleteCartItems() {
        // given
        final List<Long> cartIds = List.of(1L);

        // when
        cartItemDao.deleteCartItems(cartIds);

        // then
        final Long customerId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }
}
