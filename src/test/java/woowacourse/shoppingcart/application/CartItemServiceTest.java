package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.IdRequest;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CartItemServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("장바구니 상품 담기")
    void addCart() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductResponse product = productService.addProduct(productRequest);

        //when & then
        cartService.addCart(new CartProductRequest(product.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product.getId(), 3L, true), "greenlawn");
        CartItemsResponse products = cartService.findCart("greenlawn");
        assertThat(products.getCartItems().get(0).getQuantity()).isEqualTo(4);
    }

    @Test
    @DisplayName("장바구니 상품 목록 조회")
    void getCart() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("치킨", 20000, "http://example.com/chicken.jpg");

        ProductResponse product1 = productService.addProduct(productRequest);
        ProductResponse product2 = productService.addProduct(productRequest2);

        cartService.addCart(new CartProductRequest(product1.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product2.getId(), 1L, true), "greenlawn");

        assertThat(cartService.findCart("greenlawn").getCartItems().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 전체 삭제")
    void deleteAllCarts() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        SignUpRequest signUpRequest2 = new SignUpRequest("blue", "blue@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);
        customerService.addCustomer(signUpRequest2);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("치킨", 20000, "http://example.com/chicken.jpg");
        ProductResponse product1 = productService.addProduct(productRequest);
        ProductResponse product2 = productService.addProduct(productRequest2);

        cartService.addCart(new CartProductRequest(product1.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product2.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product1.getId(), 1L, true), "blue");

        //when
        cartService.deleteAll("greenlawn");

        //then
        assertThat(cartService.findCart("greenlawn").getCartItems().size()).isEqualTo(0);
        assertThat(cartService.findCart("blue").getCartItems().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니 복수 삭제")
    void deleteCarts() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("치킨", 20000, "http://example.com/chicken.jpg");

        ProductResponse product1 = productService.addProduct(productRequest);
        ProductResponse product2 = productService.addProduct(productRequest2);

        cartService.addCart(new CartProductRequest(product1.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product2.getId(), 1L, true), "greenlawn");

        //when
        cartService.deleteCart("greenlawn", new DeleteCartItemRequest(List.of(new IdRequest(1L), new IdRequest(2L))));

        //then
        assertThat(cartService.findCart("greenlawn").getCartItems().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("장바구니 복수 수정")
    void modifyCarts() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456q!");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("치킨", 20000, "http://example.com/chicken.jpg");

        ProductResponse product1 = productService.addProduct(productRequest);
        ProductResponse product2 = productService.addProduct(productRequest2);

        cartService.addCart(new CartProductRequest(product1.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product2.getId(), 1L, true), "greenlawn");

        //when
        cartService.updateCartItems("greenlawn", new UpdateCartItemsRequest(List.of(new UpdateCartItemRequest(1L, 3L, true))));

        //then
        assertThat(cartService.findCart("greenlawn").getCartItems().get(0).getQuantity()).isEqualTo(3L);
    }
}