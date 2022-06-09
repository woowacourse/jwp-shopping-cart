package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dto.request.CartIdRequest;
import woowacourse.shoppingcart.dto.request.CartRequest;
import woowacourse.shoppingcart.dto.response.CartResponses;
import woowacourse.shoppingcart.dto.request.DeleteProductRequest;
import woowacourse.shoppingcart.dto.response.ProductRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.request.UpdateCartRequest;
import woowacourse.shoppingcart.dto.request.UpdateCartRequests;

@SpringBootTest
@Sql("classpath:schema.sql")
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemDao cartItemDao;

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
    @DisplayName("이미 장바구니에 추가된 상품은 수량이 증가한다.")
    void addCartPlusProductQuantity() {
        // given
        productService.addProduct(new ProductRequest("치킨", 20_000, "example.com"));
        customerService.addCustomer(new SignUpRequest("rennon", "rennon@woowa.com", "123456"));
        cartService.addCart("rennon", new CartRequest(1L, 1, true));

        // when
        Long cartId = cartService.addCart("rennon", new CartRequest(1L, 1, true));

        // then
        assertThat(cartId).isEqualTo(0L);
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
        assertThat(cartResponses.getCartItems()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니 상품을 수정할 수 있다.")
    void updateCartItems() {
        // given
        customerService.addCustomer(new SignUpRequest("rennon", "rennon@woowa.com", "123456"));
        productService.addProduct(new ProductRequest("치킨1", 10_000, "http://example.com/chicken.jpg"));
        productService.addProduct(new ProductRequest("치킨2", 20_000, "http://example.com/chicken.jpg"));
        productService.addProduct(new ProductRequest("치킨3", 30_000, "http://example.com/chicken.jpg"));
        cartService.addCart("rennon", new CartRequest(1L, 1, true));
        cartService.addCart("rennon", new CartRequest(2L, 1, true));
        cartService.addCart("rennon", new CartRequest(3L, 1, true));

        // when
        UpdateCartRequests updateCartRequests = new UpdateCartRequests(List.of(new UpdateCartRequest(1L, 10, true)));
        cartService.updateCartItems("rennon", updateCartRequests);

        // then
        assertThat(cartService.findCartsByUsername("rennon").getCartItems().get(0).getQuantity()).isEqualTo(10);
        assertThat(cartService.findCartsByUsername("rennon").getCartItems().get(1).getQuantity()).isEqualTo(1);
        assertThat(cartService.findCartsByUsername("rennon").getCartItems().get(2).getQuantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("회원의 장바구니 아이템 일부를 지울 수 있다.")
    void deleteCart() {
        // given
        customerService.addCustomer(new SignUpRequest("rennon", "rennon@woowa.com", "123456"));
        productService.addProduct(new ProductRequest("치킨", 10_000, "http://example.com/chicken.jpg"));
        productService.addProduct(new ProductRequest("피자", 20_000, "http://example.com/pizza.jpg"));
        productService.addProduct(new ProductRequest("햄버거", 30_000, "http://example.com/hamburger.jpg"));
        cartService.addCart("rennon", new CartRequest(1L, 1, true));
        cartService.addCart("rennon", new CartRequest(2L, 1, true));
        cartService.addCart("rennon", new CartRequest(3L, 1, true));

        // when
        cartService.deleteCart(new DeleteProductRequest(List.of(new CartIdRequest(1L), new CartIdRequest(3L))));

        // then
        assertThat(cartService.findCartsByUsername("rennon").getCartItems()).size().isEqualTo(1);
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
        assertThat(cartService.findCartsByUsername("rennon").getCartItems()).size().isEqualTo(0);
    }
}
