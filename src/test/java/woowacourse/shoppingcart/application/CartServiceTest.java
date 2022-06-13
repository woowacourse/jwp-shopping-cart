package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.exception.ExistCartItemException;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
@Sql("/test_schema.sql")
class CartServiceTest {
    private final CustomerRequest customerRequest =
            new CustomerRequest("kth990303", "kth@@123", "케이", 23);
    private final ProductRequest productRequest1 =
            new ProductRequest("감자1", 200, "woowaPotato.com");
    private final ProductRequest productRequest2 =
            new ProductRequest("감자2", 400, "woowaPotato2.com");
    private ProductResponse productResponse1;
    private ProductResponse productResponse2;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void init() {
        customerService.addCustomer(customerRequest);

        Long product1Id = productService.addProduct(productRequest1);
        productResponse1 = ProductResponse.of(product1Id, productRequest1);

        Long product2Id = productService.addProduct(productRequest2);
        productResponse2 = ProductResponse.of(product2Id, productRequest2);

        cartService.addCart(product1Id, "kth990303");
        cartService.addCart(product2Id, "kth990303");
    }

    @DisplayName("구매자의 장바구니 상품 목록을 조회한다.")
    @Test
    void findCartsByCustomerName() {
        CartResponse cartResponse = cartService.findCartsByCustomerUsername("kth990303");
        CartItemResponse cartItemResponse1 = new CartItemResponse(productResponse1, 1);
        CartItemResponse cartItemResponse2 = new CartItemResponse(productResponse2, 1);

        assertThat(cartResponse.getCartItems())
                .usingRecursiveComparison()
                .isEqualTo(List.of(cartItemResponse1, cartItemResponse2));
    }

    @DisplayName("장바구니에 담긴 상품을 다시 장바구니에 넣을 경우 예외를 발생시킨다.")
    @Test
    void addDuplicateCartItem() {
        assertThatExceptionOfType(ExistCartItemException.class)
                .isThrownBy(() -> cartService.addCart(productResponse2.getId(), "kth990303"))
                .withMessageContaining("이미");
    }

    @DisplayName("장바구니에 존재하는 상품의 수량을 변경한다.")
    @Test
    void updateCartItemQuantity() {
        cartService.updateCartItemQuantity(3, productResponse1.getId(), "kth990303");

        CartResponse cartResponse = cartService.findCartsByCustomerUsername("kth990303");
        CartItemResponse cartItemResponse1 = new CartItemResponse(productResponse1, 3);
        CartItemResponse cartItemResponse2 = new CartItemResponse(productResponse2, 1);

        assertThat(cartResponse.getCartItems())
                .usingRecursiveComparison()
                .isEqualTo(List.of(cartItemResponse1, cartItemResponse2));
    }

    @DisplayName("장바구니에 존재하지 않는 상품의 수량을 변경할 경우 예외를 발생시킨다.")
    @Test
    void updateCartItemQuantity_invalidProduct() {
        assertThatExceptionOfType(InvalidProductException.class)
                .isThrownBy(() -> cartService.updateCartItemQuantity(3,
                        productResponse2.getId() + 100L, "kth990303"))
                .withMessageContaining("존재");
    }

    @DisplayName("장바구니를 비운다.")
    @Test
    void deleteCart() {
        cartService.deleteCart("kth990303");

        CartResponse cartResponse = cartService.findCartsByCustomerUsername("kth990303");

        assertThat(cartResponse.getCartItems().size()).isEqualTo(0);
    }

    @DisplayName("장바구니에 존재하는 상품을 삭제한다.")
    @Test
    void deleteCartItem() {
        cartService.deleteCartItem("kth990303", List.of(productResponse1.getId()));

        CartResponse cartResponse = cartService.findCartsByCustomerUsername("kth990303");
        CartItemResponse cartItemResponse1 = new CartItemResponse(productResponse1, 1);
        CartItemResponse cartItemResponse2 = new CartItemResponse(productResponse2, 1);

        assertThat(cartResponse.getCartItems())
                .usingRecursiveComparison()
                .isNotEqualTo(List.of(cartItemResponse1))
                .isEqualTo(List.of(cartItemResponse2));
    }
}