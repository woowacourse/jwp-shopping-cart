package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CustomerDao customerDao;

    @DisplayName("카트에 상품을 추가하면 상품의 재고는 감소하고 카트상품의 양은 증가해야 한다.")
    @Test
    void addCart() {
        // given
        final Long productId = productDao.save(new Product("초콜렛", 1_000, 20, "www.test.com"));
        String username = "dongho108";
        customerDao.save(Customer.of(username, "password", "01022728572", "서울시 강남구"));

        // when
        CartItemResponse cartItemResponse = cartService.addCart(productId, 10, username);
        Product product = productDao.findProductById(productId);

        // then
        assertAll(
            () -> assertThat(product.getQuantity()).isEqualTo(10),
            () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(10)
        );
    }

    @DisplayName("카트의 상품을 삭제하면 상품의 재고는 증가해야한다.")
    @Test
    void deleteCart() {
        // given
        final Long productId = productDao.save(new Product("초콜렛", 1_000, 20, "www.test.com"));
        String username = "dongho108";
        customerDao.save(Customer.of(username, "password", "01022728572", "서울시 강남구"));
        CartItemResponse cartItemResponse = cartService.addCart(productId, 10, username);

        // when
        cartService.deleteCart(username, cartItemResponse.getId());
        Product product = productDao.findProductById(productId);

        // then
        assertThat(product.getQuantity()).isEqualTo(20);
    }

    @DisplayName("카트 상품의 수량을 늘리면 상품의 재고는 감소해야한다.")
    @Test
    void addQuantity() {
        // given
        final Long productId = productDao.save(new Product("초콜렛", 1_000, 20, "www.test.com"));
        String username = "dongho108";
        customerDao.save(Customer.of(username, "password", "01022728572", "서울시 강남구"));
        CartItemResponse cartItemResponse = cartService.addCart(productId, 10, username);

        // when
        cartService.updateQuantity(cartItemResponse.getId(), 15);
        Product product = productDao.findProductById(productId);

        // then
        assertThat(product.getQuantity()).isEqualTo(5);
    }

    @DisplayName("카트 상품의 수량을 상품의 재고보다 더 많이 늘릴 수 없다.")
    @Test
    void addInvalidQuantity() {
        // given
        final Long productId = productDao.save(new Product("초콜렛", 1_000, 20, "www.test.com"));
        String username = "dongho108";

        // when
        customerDao.save(Customer.of(username, "password", "01022728572", "서울시 강남구"));
        CartItemResponse cartItemResponse = cartService.addCart(productId, 10, username);

        // then
        assertThatThrownBy(() -> cartService.updateQuantity(cartItemResponse.getId(), 25))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("카트 상품의 수량을 줄이면 상품의 재고는 증가해야한다.")
    @Test
    void removeQuantity() {
        // given
        final Long productId = productDao.save(new Product("초콜렛", 1_000, 20, "www.test.com"));
        String username = "dongho108";
        customerDao.save(Customer.of(username, "password", "01022728572", "서울시 강남구"));
        CartItemResponse cartItemResponse = cartService.addCart(productId, 10, username);

        // when
        cartService.updateQuantity(cartItemResponse.getId(), 5);
        Product product = productDao.findProductById(productId);

        // then
        assertThat(product.getQuantity()).isEqualTo(15);
    }

    @DisplayName("아이템 삭제시에 해당 customer의 cartItem이 맞지 않으면 예외를 반환해야 한다.")
    @Test
    void validateCustomerItem() {
        // given
        final Long productId = productDao.save(new Product("초콜렛", 1_000, 20, "www.test.com"));
        String username1 = "dongho108";
        String username2 = "dongho123";

        // when
        customerDao.save(Customer.of(username1, "password", "01022728572", "서울시 강남구"));
        customerDao.save(Customer.of(username2, "password", "01022728572", "서울시 강남구"));
        CartItemResponse cartItemResponse = cartService.addCart(productId, 10, username1);

        // then
        assertThatThrownBy(() -> cartService.deleteCart(username2, cartItemResponse.getId()))
            .isInstanceOf(NotInCustomerCartItemException.class);
    }
}
