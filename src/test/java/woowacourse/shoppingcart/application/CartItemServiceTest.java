package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.exception.InvalidTokenException;
import woowacourse.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.cartItem.CartItemDeleteRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemResponse;
import woowacourse.shoppingcart.dto.cartItem.CartItemUpdateRequest;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CartItemDao;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CustomerDao;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.ProductDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:init.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemServiceTest {

    private static final String CUSTOMER_EMAIL = "guest@woowa.com";
    private static final String CUSTOMER_NAME = "guest";
    private static final String CUSTOMER_PASSWORD = "qwer1234!@#$";

    private final CartItemService cartItemService;
    private final CustomerDao customerDao;

    CartItemServiceTest(final DataSource dataSource) {
        final CartItemDao cartItemDao = new CartItemDao(dataSource);
        final ProductDao productDao = new ProductDao(dataSource);
        this.customerDao = new CustomerDao(dataSource);
        this.cartItemService = new CartItemService(cartItemDao, customerDao, productDao);
    }

    @BeforeEach
    void setUp() {
        customerDao.save(new Customer(CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PASSWORD));
    }

    @DisplayName("CartItem을 추가한다.")
    @Test
    void addCartItem() {
        CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(3);
        CartItemResponse cartItemResponse = cartItemService.addCartItem(1L, 1L, cartItemUpdateRequest);
        assertThat(cartItemResponse.getQuantity()).isEqualTo(3);
    }

    @DisplayName("해당 고객의 장바구니에 담긴 CartItem 목록을 조회한다.")
    @Test
    void findCartItemsByCustomerId() {
        CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(3);
        cartItemService.addCartItem(1L, 1L, cartItemUpdateRequest);
        cartItemService.addCartItem(1L, 2L, cartItemUpdateRequest);

        List<CartItemResponse> cartItems = cartItemService.findCartItemsByCustomerId(1L);
        assertThat(cartItems).hasSize(2)
                .extracting("name", "price", "quantity")
                .containsExactly(tuple("우유", 3000, 3), tuple("바나나", 3000, 3));
    }

    @DisplayName("존재하지 않는 고객의 CartItem 목록을 조회하면 에러가 발생한다.")
    @Test
    void findCartItemsByInvalidCustomerId() {
        assertThatThrownBy(() -> cartItemService.findCartItemsByCustomerId(2L))
                .isInstanceOf(InvalidTokenException.class);
    }

    @DisplayName("삭제할 CartItem들의 productId를 받아 해당 고객의 장바구니에서 삭제한다.")
    @Test
    void deleteCartItemsByCustomerId() {
        CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(3);
        cartItemService.addCartItem(1L, 1L, cartItemUpdateRequest);
        cartItemService.addCartItem(1L, 2L, cartItemUpdateRequest);

        List<CartItemResponse> cartItems = cartItemService.findCartItemsByCustomerId(1L);
        assertThat(cartItems).hasSize(2);

        cartItemService.deleteCartItemsByCustomerId(1L, new CartItemDeleteRequest(List.of(1L, 2L)));
        List<CartItemResponse> deleteCartItems = cartItemService.findCartItemsByCustomerId(1L);
        assertThat(deleteCartItems).hasSize(0);
    }

    @DisplayName("삭제할 CartItem들의 productId가 해당 고객의 장바구니에 존재하지 않으면 에러가 발생한다.")
    @Test
    void deleteCartItemsByCustomerIdWithInvalidProductId() {
        CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(3);
        cartItemService.addCartItem(1L, 1L, cartItemUpdateRequest);
        cartItemService.addCartItem(1L, 2L, cartItemUpdateRequest);

        List<CartItemResponse> cartItems = cartItemService.findCartItemsByCustomerId(1L);
        assertThat(cartItems).hasSize(2);

        assertThatThrownBy(
                () -> cartItemService.deleteCartItemsByCustomerId(
                        1L, new CartItemDeleteRequest(List.of(1L, 2L, 3L))))
                .isInstanceOf(NotInCustomerCartItemException.class);
    }
}
