package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.cartitem.dao.CartItemDao;
import woowacourse.shoppingcart.cartitem.domain.CartItem;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.dto.ThumbnailImage;
import woowacourse.shoppingcart.product.dao.ProductDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoTest(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        cartItemDao = new CartItemDao(dataSource);
        productDao = new ProductDao(dataSource);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("맥북", 1_000, 10L, new ThumbnailImage("test.com", "이미지입니다.")));
        productDao.save(new Product("애플워치", 2_000,  10L, new ThumbnailImage("test2.com", "이미지입니다.")));

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 1L, 1);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)", 1L, 2L, 1);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, productId, 1);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("소비자 id를 넣으면, 해당 소비자가 담은 장바구니 아이템 객체 목록을 반환한다.")
    @Test
    void findProductByCustomerId() {
        // given
        final Long customerId = 1L;

        // when
        final List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customerId);
        final List<Long> cartItemIds = cartItems.stream()
                .map(CartItem::getId)
                .collect(Collectors.toUnmodifiableList());
        // then
        assertThat(cartItemIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id와 cartItem Id를 넣으면, 해당 장바구니 아이템 객체를 가져온다.")
    @Test
    void findIdsByCustomerId() {
        // given
        final Long customerId = 1L;
        final Long cartItemId = 1L;

        // when
        final CartItem cartItem = cartItemDao.findByCustomerId(customerId, cartItemId);

        // then
        assertThat(cartItem.getProduct().getName()).isEqualTo("맥북");
    }

    @DisplayName("cartItemId와 customerId를 기반으로 quantity를 변경한다.")
    @Test
    void updateQuantityByCustomerId() {
        //given
        final Long customerId = 1L;
        final Long cartItemId = 1L;
        final Integer quantity = 10;
        //when
        cartItemDao.updateQuantityByCustomerId(customerId, cartItemId, quantity);
        //then
        final CartItem cartItem = cartItemDao.findByCustomerId(customerId, cartItemId);
        assertThat(cartItem.getQuantity()).isEqualTo(quantity);
    }

    @DisplayName("cartItem id 리스트를 받으면 해당 장바구니 아이템을 삭제한다.")
    @Test
    void deleteCartItemByIds() {
        //given
        final List<Long> cartItemIds = List.of(1L, 2L);
        //when
        cartItemDao.deleteCartItemByIds(cartItemIds);
        //then
        final List<CartItem> cartItems = cartItemDao.findAllByCustomerId(1L);
        assertThat(cartItems).isEmpty();
    }

    @DisplayName("cartItem id 리스트를 받으면 해당 장바구니 아이템들을 받아온다.")
    @Test
    void findByIds() {
        //given
        final List<Long> cartItemIds = List.of(1L, 2L);
        //when
        final List<CartItem> cartItems = cartItemDao.findByIds(cartItemIds);
        final List<Long> savedCartItemIds = cartItems.stream()
                .map(CartItem::getId)
                .collect(Collectors.toUnmodifiableList());
        //then
        assertThat(savedCartItemIds).containsExactly(1L,2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 아이템을 삭제한다.")
    @Test
    void deleteCartItem() {
        // given
        final Long cartId = 1L;
        final int beforeSize = 2;
        // when
        cartItemDao.deleteCartItem(cartId);
        // then
        final Long customerId = 1L;
        final List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customerId);

        assertThat(cartItems.size()).isEqualTo(beforeSize - 1);
    }
}
