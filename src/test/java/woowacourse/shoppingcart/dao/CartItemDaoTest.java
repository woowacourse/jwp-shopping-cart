package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

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
import woowacourse.shoppingcart.domain.product.vo.ThumbnailImage;
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

    @DisplayName("장바구니 제품의 희망 수량을 변동한다.")
    @Test
    void updateQuantity() {
        // given
        final long customerId = 1L;
        Product product = productStock1.getProduct();
        CartItem cartItem = new CartItem(product, new Quantity(10));
        long id = cartItemDao.addCartItem(customerId, cartItem);

        // when
        cartItemDao.update(new CartItem(id, cartItem.getProduct(), new Quantity(12)));

        // then
        CartItemEntity cartItemEntity = cartItemDao.findCartItemById(id);
        assertThat(cartItemEntity.getQuantity()).isEqualTo(12);

    }
}
