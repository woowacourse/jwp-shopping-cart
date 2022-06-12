package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartResponses;
import woowacourse.shoppingcart.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemRequests;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;
import woowacourse.shoppingcart.dto.UpdateCartItemRequests;

@SpringBootTest
@Sql(value = "/sql/ClearCart.sql")
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProductDao productDao;

    @DisplayName("username의 모든 장바구니 정보 조회")
    @Test
    void findCartsByUsername() {
        // given
        Customer customer = createUser("alien", "alien@woowa.com", "12345678");
        Product product = createProduct("apple", 1000, "imageUrl.jpg");
        Long cartId = cartService.addCart(new AddCartItemRequest(product.getId(), 10L, true), customer.getUsername());

        // when
        CartResponses responses = cartService.findCartsByUsername(customer.getUsername());

        // then
        assertAll(
                () -> assertThat(responses.getCartItems()).hasSize(1),
                () -> assertThat(responses.getCartItems().get(0).getId()).isEqualTo(cartId)
        );
    }

    @DisplayName("username의 장바구니 추가")
    @Test
    void addCart() {
        // given
        Customer customer = createUser("alien", "alien@woowa.com", "12345678");
        Product product = createProduct("apple", 1000, "imageUrl.jpg");

        // when
        Long cartId = cartService.addCart(new AddCartItemRequest(product.getId(), 10L, true), customer.getUsername());

        // then
        assertThat(cartService.findCartsByUsername(customer.getUsername()).getCartItems()).hasSize(1);
    }

    @DisplayName("username의 장바구니 id를 가진 정보 수정")
    @Test
    void updateCartItems() {
        // given
        Customer customer = createUser("alien", "alien@woowa.com", "12345678");
        Product product = createProduct("apple", 1000, "imageUrl.jpg");
        Long cartId = cartService.addCart(new AddCartItemRequest(product.getId(), 10L, true), customer.getUsername());
        UpdateCartItemRequests updateCartItemRequests = new UpdateCartItemRequests(
                List.of(new UpdateCartItemRequest(cartId, 5L, false)));

        // when
        CartResponses cartResponses = cartService.updateCartItems(updateCartItemRequests, customer.getUsername());

        // then
        assertAll(
                () -> assertThat(cartResponses.getCartItems().get(0).getQuantity()).isEqualTo(5L),
                () -> assertThat(cartResponses.getCartItems().get(0).isChecked()).isFalse()
        );
    }

    @DisplayName("username의 장바구니 id의 상품 제거")
    @Test
    void deleteCart() {
        // given
        Customer customer = createUser("alien", "alien@woowa.com", "12345678");
        Product product = createProduct("apple", 1000, "imageUrl.jpg");
        Long cartId = cartService.addCart(new AddCartItemRequest(product.getId(), 10L, true), customer.getUsername());

        // when
        cartService.deleteCart(customer.getUsername(), cartId);

        // then
        assertThat(cartService.findCartsByUsername(customer.getUsername()).getCartItems()).hasSize(0);
    }

    @DisplayName("username의 모든 장바구니 상품 제거")
    @Test
    void deleteAllCart() {
        // given
        Customer customer = createUser("alien", "alien@woowa.com", "12345678");
        Product product1 = createProduct("apple", 1000, "imageUrl1.jpg");
        Product product2 = createProduct("banana", 2000, "imageUrl2.jpg");
        cartService.addCart(new AddCartItemRequest(product1.getId(), 10L, true), customer.getUsername());
        cartService.addCart(new AddCartItemRequest(product2.getId(), 10L, true), customer.getUsername());

        // when
        cartService.deleteAllCart(customer.getUsername());

        // then
        assertThat(cartService.findCartsByUsername(customer.getUsername()).getCartItems()).hasSize(0);
    }

    @DisplayName("원하는 장바구니 id를 입력하면 해당 장바구니 id를 가진 상품을 모두 제거")
    @Test
    void deleteAllCartByProducts() {
        // given
        Customer customer = createUser("alien", "alien@woowa.com", "12345678");
        Product product1 = createProduct("apple", 1000, "imageUrl1.jpg");
        Product product2 = createProduct("banana", 2000, "imageUrl2.jpg");
        Long cartId1 = cartService.addCart(new AddCartItemRequest(product1.getId(), 10L, true), customer.getUsername());
        Long cartId2 = cartService.addCart(new AddCartItemRequest(product2.getId(), 10L, true), customer.getUsername());
        DeleteCartItemRequests deleteCartItemRequests = new DeleteCartItemRequests(
                List.of(new DeleteCartItemRequest(cartId1)));

        // when
        cartService.deleteAllCartByProducts(customer.getUsername(), deleteCartItemRequests);

        // then
        List<CartResponse> cartItems = cartService.findCartsByUsername(customer.getUsername()).getCartItems();
        assertAll(
                () -> assertThat(cartItems).hasSize(1),
                () -> assertThat(cartItems.get(0).getId()).isEqualTo(cartId2)
        );
    }

    private Customer createUser(String username, String email, String password) {
        return customerDao.save(new Customer(username, email, password));
    }

    private Product createProduct(String name, int price, String imageUrl) {
        Long id = productDao.save(new Product(name, price, imageUrl));
        return new Product(id, name, price, imageUrl);
    }
}
