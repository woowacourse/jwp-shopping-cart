package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.ProductStock;
import woowacourse.shoppingcart.domain.product.ThumbnailImage;
import woowacourse.shoppingcart.entity.CartItemEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;
    private ProductStock productStock1;
    private ProductStock productStock2;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productStock1 = productDao.save(new Product("banana", 1_000, new ThumbnailImage("woowa1.com", "이미지")), 10);
        productStock2 = productDao.save(new Product("apple", 2_000, new ThumbnailImage("woowa2.com", "이미지")), 10);

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 10);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 2L, 10);
    }

    @DisplayName("카트에 아이템을 담으면, 담은 카트 아이템의 아이디를 반환한다.")
    @Test
    void addCartItem() {
        // given
        final long customerId = 1L;
        Product product = productStock1.getProduct();
        CartItem cartItem = new CartItem(product, new Quantity(10));

        // when
        long id = cartItemDao.addCartItem(customerId, cartItem);

        // then
        assertThat(id).isEqualTo(3L);
    }

    @DisplayName("아이디를 이용해 cartItem의 엔티티를 알 수 있다.")
    @Test
    void findById() {
        CartItemEntity cartItemEntity = cartItemDao.findCartItemById(1L);
        assertThat(cartItemEntity)
            .extracting("id", "customerId", "productId", "quantity")
            .containsExactly(1L, 1L, 1L, 10);
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
        cartItemDao.deleteCartItem(cartId);

        // then
        final Long customerId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }
}
