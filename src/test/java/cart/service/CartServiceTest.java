package cart.service;

import cart.dao.JdbcCartDao;
import cart.dao.JdbcProductDao;
import cart.dao.JdbcUserDao;
import cart.domain.cart.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
@Sql(scripts = {"classpath:test.sql"})
class CartServiceTest {

    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;

    public CartServiceTest(@Autowired final JdbcTemplate jdbcTemplate) {
        this.productService = new ProductService(new JdbcProductDao(jdbcTemplate));
        this.userService = new UserService(new JdbcUserDao(jdbcTemplate));
        this.cartService = new CartService(productService, userService, new JdbcCartDao(jdbcTemplate));
    }

    @Test
    @DisplayName("상품을 저장한다")
    void save() {
        final long productId = productService.save("치킨", 1000, null);
        final long userId = userService.save("IO@mail.com", "12121212");

        assertDoesNotThrow(() -> cartService.save(userId, productId));
    }

    @Test
    @DisplayName("사용자 ID로 장바구니의 물건을 조회한다.")
    void findByUserId() {
        final long productId1 = productService.save("치킨", 1000, null);
        final long productId2 = productService.save("피자", 1000, null);
        final long userId = userService.save("ASH@mail.com", "12121212");
        cartService.save(userId, productId1);
        cartService.save(userId, productId2);

        final List<CartItem> actual = cartService.findByUserId(userId);

        assertThat(actual).extracting("id")
                .containsExactly(productId1, productId2);
    }

    @Test
    @DisplayName("장바구니의 상품을 삭제한다")
    void delete() {
        final long productId = productService.save("치킨", 1000, null);
        final long userId = userService.save("ASH@mail.com", "12121212");
        final long id = cartService.save(userId, productId);

        cartService.delete(id);

        final List<CartItem> cartItems = cartService.findByUserId(userId);
        assertThat(cartItems.size()).isEqualTo(0);
    }
}
