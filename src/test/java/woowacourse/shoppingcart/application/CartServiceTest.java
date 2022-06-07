package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponses;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;

@SpringBootTest
@Sql("classpath:schema.sql")
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    void addCart() {
        // given
        customerService.addCustomer(new SignUpRequest("rennon", "rennon@woowa.com", "123456"));
        productService.addProduct(new ProductRequest("치킨", 20_000, "http://example.com/chicken.jpg"));

        // when
        Long id = cartService.addCart("rennon", new CartRequest(1L, 1, true));

        // then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    @DisplayName("회원 이름으로 장바구니 목록을 불러올 수 있다.")
    void findCartsByUsername() {
        // given
        customerService.addCustomer(new SignUpRequest("rennon", "rennon@woowa.com", "123456"));
        productService.addProduct(new ProductRequest("치킨", 20_000, "http://example.com/chicken.jpg"));
        cartService.addCart("rennon", new CartRequest(1L, 1, true));

        // when
        CartResponses cartResponses = cartService.findCartsByUsername("rennon");

        // then
        assertThat(cartResponses.getProducts()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("회원의 장바구니를 비울 수 있다.")
    void deleteAllCart() {
        // given
        customerService.addCustomer(new SignUpRequest("rennon", "rennon@woowa.com", "123456"));
        productService.addProduct(new ProductRequest("치킨", 20_000, "http://example.com/chicken.jpg"));
        cartService.addCart("rennon", new CartRequest(1L, 1, true));

        // when
        cartService.deleteAllCart("rennon");

        // then
        assertThat(cartService.findCartsByUsername("rennon").getProducts()).size().isEqualTo(0);
    }
}
