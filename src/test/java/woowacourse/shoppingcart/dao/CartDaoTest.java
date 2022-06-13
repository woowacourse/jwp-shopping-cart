package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Username;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartDaoTest {

    private final CartDao cartDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartDaoTest(DataSource dataSource) {
        cartDao = new CartDao(dataSource);
        customerDao = new CustomerDao(dataSource);
        productDao = new ProductDao(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @BeforeEach
    void setUp() {
        Long customerId = customerDao.save(
                new Customer(new Username("rennon"), new Email("rennon@woowa.com"), new Password("123456"))).getId();
        Long productId1 = productDao.save(new Product("banana", 1_000, "woowa1.com")).getId();
        Long productId2 = productDao.save(new Product("apple", 2_000, "woowa2.com")).getId();
        Long productId3 = productDao.save(new Product("chicken", 3_000, "woowa3.com")).getId();

        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity, checked) VALUES(?, ?, ?, ?)",
                customerId,
                productId1, 1, true);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity, checked) VALUES(?, ?, ?, ?)",
                customerId,
                productId2, 1, true);
        jdbcTemplate.update("INSERT INTO cart_item(customer_id, product_id, quantity, checked) VALUES(?, ?, ?, ?)",
                customerId,
                productId3, 1, true);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다.")
    @Test
    void addCartItem() {
        // given
        Long customerId = 1L;
        Long productId = 1L;
        Integer quantity = 1;
        Boolean checked = true;
        Product product = productDao.findProductById(productId);

        // when
        CartItem cartItem = cartDao.addCartItem(customerId, new CartItem(product, quantity, checked));

        // then
        assertThat(cartItem.getId()).isEqualTo(4L);
    }

    @Test
    @DisplayName("회원의 장바구니에 상품이 존재한다.")
    void existByProductIdWithTrue() {
        // given
        Long customerId = 1L;

        // when
        boolean result = cartDao.existByProductId(customerId, 1L);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("회원의 장바구니에 상품이 존재하지 않는다.")
    void existByProductIdWithFalse() {
        // given
        Long customerId = 1L;

        // when
        boolean result = cartDao.existByProductId(customerId, 10L);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니를 가져온다.")
    @Test
    void findIdsByCustomerId() {
        // given
        Long customerId = 1L;

        // when
        Cart cart = cartDao.findByCustomerId(customerId);
        List<Long> cartIds = cart.getValue().stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());

        // then
        assertThat(cartIds).containsExactly(1L, 2L, 3L);
    }

    @Test
    @DisplayName("장바구니 아이템을 상품 id로 수정한다.")
    void updateCartItemByProductId() {
        //given
        Long customerId = 1L;
        Product product = new Product(1L, "banana", 1_000, "woowa1.com");
        CartItem cartItem = new CartItem(product, 2, true);

        // when
        cartDao.updateCartItemByProductId(customerId, cartItem);
        Integer quantity = jdbcTemplate.queryForObject(
                "SELECT quantity FROM cart_item WHERE customer_id = ? and product_id = ?",
                Integer.class, customerId, 1L);

        // then
        assertThat(quantity).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 아이템을 수정한다.")
    void updateCartItem() {
        // given
        Long customerId = 1L;

        // when
        cartDao.updateCartItems(List.of(new CartItem(1L, 1, false), new CartItem(3L, 3, true)));
        Cart cart = cartDao.findByCustomerId(customerId);
        CartItem firstCartItemItem = cart.getValue().get(0);
        CartItem thirdCartItemItem = cart.getValue().get(2);

        // then
        Assertions.assertAll(
                () -> assertThat(firstCartItemItem.isChecked()).isFalse(),
                () -> assertThat(thirdCartItemItem.getQuantity()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("장바구니의 아이템 일부를 삭제한다.")
    void deleteCartItem() {
        // given
        Long customerId = 1L;

        // when
        cartDao.deleteCartItems(List.of(1L, 2L));
        Cart cart = cartDao.findByCustomerId(customerId);
        List<Long> cartIds = cart.getValue().stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());

        // then
        assertThat(cartIds).containsExactly(3L);
    }

    @Test
    @DisplayName("장바구니의 아이템 전체를 삭제한다.")
    void deleteAllCartItem() {
        // given
        Long customerId = 1L;

        // when
        cartDao.deleteAllCartItem(customerId);
        Cart cart = cartDao.findByCustomerId(customerId);

        // then
        assertThat(cart.getValue()).size().isEqualTo(0);
    }
}
