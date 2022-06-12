package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.notfound.NotFoundCartItemException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    private Long bananaId;
    private Long appleId;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        bananaId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        appleId = productDao.save(new Product("apple", 2_000, "woowa2.com"));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, bananaId, 10);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, appleId, 3);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다.")
    @Test
    void addCartItem() {
        // when
        final Long cartId = cartItemDao.addCartItem(1L, bananaId);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("카트에 없는 아이템을 담으려고 하면, 예외를 발생시킨다.")
    @Test
    void addNotExistCartItem() {
        // when & then
        assertThatThrownBy(() -> cartItemDao.addCartItem(1L, 10L))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("없는 사용자의 카트에 담으려고 하면, 예외를 발생시킨다.")
    @Test
    void addCartItemWithNotExistCustomer() {
        // when & then
        assertThatThrownBy(() -> cartItemDao.addCartItem(10L, bananaId))
                .isInstanceOf(DataIntegrityViolationException.class);
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

    @DisplayName("장바구니 ID로 담겨있는 상품 ID를 가져온다.")
    @Test
    void getProductIdById() {
        // when
        final Long productId = cartItemDao.getProductIdById(1L);

        // then
        assertThat(productId).isEqualTo(bananaId);
    }

    @DisplayName("없는 장바구니 ID로 찾을 경우, 예외를 발생시킨다.")
    @Test
    void getProductIdByNotExistId() {
        // when & then
        assertThatThrownBy(() -> cartItemDao.getProductIdById(10L))
                .isInstanceOf(NotFoundCartItemException.class);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
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
}
