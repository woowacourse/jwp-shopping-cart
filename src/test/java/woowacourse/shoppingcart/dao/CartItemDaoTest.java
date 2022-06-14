package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.cart.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.cart.CartItemDto;
import woowacourse.shoppingcart.dto.product.ProductCreateRequest;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다.")
    @Test
    void addCartItem() {
        // given
        Long bananaId = productDao.save(new ProductCreateRequest("banana", 1_000, "woowa1.com", 10));
        Long appleId = productDao.save(new ProductCreateRequest("apple", 2_000, "woowa2.com", 10));
        Long tomatoId = productDao.save(new ProductCreateRequest("tomato", 2_000, "woowa2.com", 10));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, bananaId, 3);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, appleId, 4);

        final Long customerId = 1L;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, new CartItemCreateRequest(tomatoId, 2));

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("Customer Id를 넣으면 해당 장바구니 아이템 목록을 가져온다.")
    @Test
    void findCartItemsByCustomerId() {
        // given
        Long bananaId = productDao.save(new ProductCreateRequest("banana", 1_000, "woowa1.com", 10));
        Long appleId = productDao.save(new ProductCreateRequest("apple", 2_000, "woowa2.com", 10));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, bananaId, 3);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, appleId, 4);

        final Long customerId = 1L;

        // when
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);

        // then
        assertAll(
                () -> assertThat(cartItems).hasSize(2),
                () -> assertThat(cartItems.get(0).getCount()).isEqualTo(3),
                () -> assertThat(cartItems.get(0).getProductId()).isEqualTo(bananaId),
                () -> assertThat(cartItems.get(1).getCount()).isEqualTo(4),
                () -> assertThat(cartItems.get(1).getProductId()).isEqualTo(appleId)
        );
    }

    @DisplayName("Customer Id를 넣으면 해당 장바구니 디테일한 목록을 가져온다.")
    @Test
    void findCartItemDetailsByCustomerId() {
        // given
        Long bananaId = productDao.save(new ProductCreateRequest("banana", 1_000, "woowa1.com", 10));
        Long appleId = productDao.save(new ProductCreateRequest("apple", 2_000, "woowa2.com", 10));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, bananaId, 3);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, appleId, 4);

        final Long customerId = 1L;

        // when
        List<CartItemDto> cartItems = cartItemDao.findCartItemDetailsByCustomerId(customerId);

        // then
        assertAll(
                () -> assertThat(cartItems).hasSize(2),
                () -> assertThat(cartItems.get(0).getProductId()).isEqualTo(bananaId),
                () -> assertThat(cartItems.get(0).getName()).isEqualTo("banana"),
                () -> assertThat(cartItems.get(0).getPrice()).isEqualTo(1_000),
                () -> assertThat(cartItems.get(0).getThumbnailUrl()).isEqualTo("woowa1.com"),
                () -> assertThat(cartItems.get(0).getQuantity()).isEqualTo(10),
                () -> assertThat(cartItems.get(0).getCount()).isEqualTo(3),

                () -> assertThat(cartItems.get(1).getProductId()).isEqualTo(appleId),
                () -> assertThat(cartItems.get(1).getName()).isEqualTo("apple"),
                () -> assertThat(cartItems.get(1).getPrice()).isEqualTo(2_000),
                () -> assertThat(cartItems.get(1).getThumbnailUrl()).isEqualTo("woowa2.com"),
                () -> assertThat(cartItems.get(1).getQuantity()).isEqualTo(10),
                () -> assertThat(cartItems.get(1).getCount()).isEqualTo(4)
        );
    }

    @DisplayName("장바구니의 count를 업데이트한다.")
    @Test
    void updateCount() {
        // given
        Long bananaId = productDao.save(new ProductCreateRequest("banana", 1_000, "woowa1.com", 10));
        Long appleId = productDao.save(new ProductCreateRequest("apple", 2_000, "woowa2.com", 10));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, bananaId, 3);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, appleId, 4);

        final Long customerId = 1L;

        // when
        cartItemDao.updateCount(customerId, appleId, 1);
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);

        // then
        Integer resultCount = cartItems.stream()
                .filter(cartItem -> cartItem.getProductId().equals(appleId))
                .map(CartItem::getCount)
                .findFirst().get();

        assertThat(resultCount).isEqualTo(1);
    }

    @DisplayName("Customer Id와 Product Id를 통해 장바구니에서 상품을 제거한다.")
    @Test
    void deleteCartItemByCustomerIdAndProductId() {
        // given
        Long bananaId = productDao.save(new ProductCreateRequest("banana", 1_000, "woowa1.com", 10));
        Long appleId = productDao.save(new ProductCreateRequest("apple", 2_000, "woowa2.com", 10));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, bananaId, 3);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, appleId, 4);

        final Long customerId = 1L;

        // when
        cartItemDao.deleteCartItemByCustomerIdAndProductId(customerId, bananaId);

        // then
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        assertThat(cartItems).hasSize(1);
    }

    @DisplayName("해당 CustomerId와 ProductId로 담겨있는 장바구니 아이템이 존재하는지 확인한다.")
    @Test
    void existsIdByCustomerIdAndProductId() {
        // given
        Long bananaId = productDao.save(new ProductCreateRequest("banana", 1_000, "woowa1.com", 10));
        Long appleId = productDao.save(new ProductCreateRequest("apple", 2_000, "woowa2.com", 10));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, bananaId, 3);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)", 1L, appleId, 4);

        final Long customerId = 1L;

        // when then
        assertAll(
                () -> assertThat(cartItemDao.existIdByCustomerIdAndProductId(customerId, bananaId)).isTrue(),
                () -> assertThat(cartItemDao.existIdByCustomerIdAndProductId(customerId, 300L)).isFalse()
        );
    }
}
