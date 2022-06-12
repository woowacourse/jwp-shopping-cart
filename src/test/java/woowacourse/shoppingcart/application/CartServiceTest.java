package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import woowacourse.shoppingcart.application.dto.CartItemResponse;
import woowacourse.shoppingcart.application.dto.CartResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.support.test.ExtendedApplicationTest;

@ExtendedApplicationTest
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CustomerDao customerDao;

    private Long customerId;
    private Long productAId;
    private Long productBId;

    @BeforeEach
    void setUp() {
        productAId = productDao.save(new Product("banana", 1_000, "http://woowa1.com", "banana-description"));
        productBId = productDao.save(new Product("apple", 2_000, "http://woowa2.com", "apple-description"));
        customerId = customerDao.save(
            Customer.fromInput("username", "password1234!", "example@email.com", "some-address", "010-1234-1234")
        ).orElseThrow();

    }

    @DisplayName("상품을 추가한다.")
    @Test
    void addCartItem() {
        // given
        int quantity = 10;
        // when
        final Long cartItemId = cartService.addCart(productAId, quantity, customerId);
        // then
        assertThat(cartItemId).isNotNull();
    }

    @DisplayName("회원의 장바구니를 조회한다.")
    @Test
    void findCartItemsByCustomerId() {
        // given
        cartService.addCart(productAId, 15, customerId);
        cartService.addCart(productBId, 5, customerId);
        // when
        final CartResponse cartResponse = cartService.findCartsByCustomerId(customerId);
        // then
        assertThat(cartResponse.getItemResponses()).hasSize(2);
    }

    @DisplayName("회원의 장바구니 품목을 삭제한다.")
    @Test
    void deleteCartItem() {
        // given
        final Long cartItemId = cartService.addCart(productAId, 15, customerId);
        // when
        cartService.deleteCart(customerId, cartItemId);
        final CartResponse cartResponse = cartService.findCartsByCustomerId(customerId);
        // then
        assertThat(cartResponse.getItemResponses()).hasSize(0);
    }

    @DisplayName("회원의 장바구니 품목의 수량을 수정한다.")
    @Test
    void updateCartItemQuantity() {
        // given
        final Long cartItemId = cartService.addCart(productAId, 15, customerId);

        // when
        cartService.updateCartItemQuantity(customerId, cartItemId, 200);

        // then
        final List<CartItemResponse> itemResponses = cartService.findCartsByCustomerId(customerId).getItemResponses();
        Assertions.assertAll(
            () -> assertThat(itemResponses).hasSize(1),
            () -> assertThat(itemResponses.get(0).getQuantity()).isEqualTo(200)
        );
    }
}