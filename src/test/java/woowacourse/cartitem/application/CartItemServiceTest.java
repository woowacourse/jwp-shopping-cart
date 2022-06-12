package woowacourse.cartitem.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.cartitem.dto.CartItemAddRequest;
import woowacourse.cartitem.dto.CartItemResponse.InnerCartItemResponse;
import woowacourse.cartitem.exception.InvalidCartItemException;
import woowacourse.customer.application.CustomerService;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.product.application.ProductService;
import woowacourse.product.dto.ProductRequest;

@Transactional
@SpringBootTest
public class CartItemServiceTest {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    private String username;
    private Long productId1;
    private Long productId2;

    @BeforeEach
    void setUp() {
        customerService.save(new SignupRequest("username", "password", "01000001111", "서울시"));
        username = customerService.findCustomerByUsername("username").getUsername();
        productId1 = productService.addProduct(new ProductRequest("짱구", 100_000_000, 10, "jjanggu.jpg"));
        productId2 = productService.addProduct(new ProductRequest("짱아", 10_000_000, 10, "jjanga.jpg"));
    }

    @DisplayName("카트에 아이템을 추가한다.")
    @Test
    void addCartItem() {
        final CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(productId1, 1);

        final Long cartItemId = cartItemService.addCartItem(username, cartItemAddRequest);

        assertThat(cartItemId).isNotNull();
    }

    @DisplayName("카트 아이템 목록을 조회한다.")
    @Test
    void findCartItems() {
        cartItemService.addCartItem(username, new CartItemAddRequest(productId1, 1));
        cartItemService.addCartItem(username, new CartItemAddRequest(productId2, 1));

        final List<Long> productIds = cartItemService.findCartItemsByCustomerName(username).getCartItems()
            .stream()
            .map(InnerCartItemResponse::getProductId)
            .collect(Collectors.toList());

        assertThat(productIds).contains(productId1, productId2);
    }

    @DisplayName("카트 아이템 수량을 수정한다.")
    @Test
    void updateCartItem() {
        final Long cartItemId = cartItemService.addCartItem(username, new CartItemAddRequest(productId1, 1));
        assertDoesNotThrow(() -> cartItemService.updateQuantity(username, cartItemId, 5));
    }

    @DisplayName("카트 아이템을 삭제한다.")
    @Test
    void deleteCartItem() {
        final Long cartItemId = cartItemService.addCartItem(username, new CartItemAddRequest(productId1, 1));
        cartItemService.deleteCartItem(username, cartItemId);

        assertThatThrownBy(() -> cartItemService.findCartItemById(cartItemId))
            .isInstanceOf(InvalidCartItemException.class);
    }
}
