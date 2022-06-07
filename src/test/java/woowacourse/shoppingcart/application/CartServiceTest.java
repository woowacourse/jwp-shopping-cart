package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Id;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CartServiceTest {

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
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        Product product = productService.addProduct(productRequest);

        //when & then
        assertThat(cartService.addCart(new CartProductRequest(product.getId(), 1L, true), "greenlawn"))
                .isNotNull();
    }

    @Test
    @DisplayName("장바구니 상품 목록 조회")
    void getCart() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("치킨", 20000, "http://example.com/chicken.jpg");

        Product product1 = productService.addProduct(productRequest);
        Product product2 = productService.addProduct(productRequest2);

        cartService.addCart(new CartProductRequest(product1.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product2.getId(), 1L, true), "greenlawn");

        assertThat(cartService.getCart("greenlawn").getProducts().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 전체 삭제")
    void deleteAllCarts() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("치킨", 20000, "http://example.com/chicken.jpg");

        Product product1 = productService.addProduct(productRequest);
        Product product2 = productService.addProduct(productRequest2);

        cartService.addCart(new CartProductRequest(product1.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product2.getId(), 1L, true), "greenlawn");
        //when
        cartService.deleteAll();

        //then
        assertThat(cartService.getCart("greenlawn").getProducts().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("장바구니 복수 삭제")
    void deleteCarts() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("치킨", 20000, "http://example.com/chicken.jpg");

        Product product1 = productService.addProduct(productRequest);
        Product product2 = productService.addProduct(productRequest2);

        cartService.addCart(new CartProductRequest(product1.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product2.getId(), 1L, true), "greenlawn");

        //when
        cartService.deleteCart(new DeleteProductRequest(List.of(new Id(1L), new Id(2L))));

        //then
        assertThat(cartService.getCart("greenlawn").getProducts().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("장바구니 복수 수정")
    void modifyCarts() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("치킨", 20000, "http://example.com/chicken.jpg");

        Product product1 = productService.addProduct(productRequest);
        Product product2 = productService.addProduct(productRequest2);

        cartService.addCart(new CartProductRequest(product1.getId(), 1L, true), "greenlawn");
        cartService.addCart(new CartProductRequest(product2.getId(), 1L, true), "greenlawn");

        //when
        cartService.modify(new ModifyProductRequests(List.of(new ModifyProductRequest(1L, 3L, true))));

        //then
        assertThat(cartService.getCart("greenlawn").getProducts().get(0).getQuantity()).isEqualTo(3L);
    }
}