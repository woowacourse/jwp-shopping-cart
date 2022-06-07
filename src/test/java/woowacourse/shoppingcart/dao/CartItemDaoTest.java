package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Image;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.Username;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private static final long CUSTOMER_ID = 1L;
    private static final int stockQuantity = 10;
    private static final int cartItemQuantity = 3;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;
    private Long PRODUCT_ID1;
    private Long PRODUCT_ID2;
    private Long CART_ITEM_ID1;
    private Long CART_ITEM_ID2;
    private Image image = new Image("ImageUrl", "ImageAlt");

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        productDao = new ProductDao(jdbcTemplate);
        cartItemDao = new CartItemDao(jdbcTemplate, productDao);
    }

    @BeforeEach
    void setUp() {
        PRODUCT_ID1 = productDao.save(new Product("banana", 1_000, stockQuantity, image));
        PRODUCT_ID2 = productDao.save(new Product("apple", 2_000, stockQuantity, image));

        final Customer customer = new Customer(CUSTOMER_ID, Email.of("test@gmail.com"),
                Password.ofWithEncryption("password1!"), Username.of("aki"));
        CART_ITEM_ID1 = cartItemDao.addCartItem(customer, PRODUCT_ID1, cartItemQuantity);
        CART_ITEM_ID2 = cartItemDao.addCartItem(customer, PRODUCT_ID2, cartItemQuantity);
    }

    @DisplayName("카트에 아이템을 추가한다.")
    @Test
    void addCartItem() {
        // given
        final Customer customer = new Customer(CUSTOMER_ID,
                Email.of("test@gmail.com"),
                Password.ofWithEncryption("password1!"),
                Username.of("aki"));
        final Long productId3 = productDao.save(new Product("banana", 1_000, stockQuantity, image));

        // when
        final Long cartId = cartItemDao.addCartItem(customer, productId3, cartItemQuantity);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("고객의 장바구니 물품들을 모두 가져온다.")
    @Test
    void findCartsByCustomerId() {
        // when
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(CUSTOMER_ID);

        // then
        assertAll(
                () -> assertThat(cartItems.get(0).getId()).isEqualTo(CART_ITEM_ID1),
                () -> assertThat(cartItems.get(1).getId()).isEqualTo(CART_ITEM_ID2)
        );
    }

    @DisplayName("고객의 장바구니 물품을 가져온다.")
    @Test
    void findById() {
        // when
        final CartItem cartItem = cartItemDao.findById(CUSTOMER_ID, CART_ITEM_ID1);

        // then
        assertAll(
                () -> assertThat(cartItem.getId()).isEqualTo(CART_ITEM_ID1),
                () -> assertThat(cartItem.getProduct().getName()).isEqualTo("banana"),
                () -> assertThat(cartItem.getQuantity()).isEqualTo(cartItemQuantity)
        );
    }

    @DisplayName("고객의 장바구니 물품을 삭제한다.")
    @Test
    void deleteCartItem() {
        // when
        cartItemDao.deleteCartItem(CART_ITEM_ID1);
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(CUSTOMER_ID);

        // then
        assertThat(cartItems.get(0).getId()).isEqualTo(CART_ITEM_ID2);
    }

    @DisplayName("고객의 장바구니 물품 수량을 수정한다.")
    @Test
    void updateQuantity() {
        // when
        final int newQuantity = 20;
        cartItemDao.updateQuantity(CUSTOMER_ID, CART_ITEM_ID1, newQuantity);

        // then
        final CartItem cartItem = cartItemDao.findById(CUSTOMER_ID, CART_ITEM_ID1);
        assertThat(cartItem.getQuantity()).isEqualTo(newQuantity);
    }
}
