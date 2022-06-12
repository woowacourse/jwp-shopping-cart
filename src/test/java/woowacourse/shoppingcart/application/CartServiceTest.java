package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProductDao productDao;

    private Customer customer = Customer.builder()
            .username("customer")
            .password("ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f")
            .phoneNumber("01012345678")
            .address("SeongDam building")
            .build();

    private Product product = Product.builder()
            .productName("beer")
            .price(3_000)
            .stock(10)
            .build();

    @DisplayName("카트 아이템 추가")
    @Test
    void add() {
        Long customerId = customerDao.save(customer);
        Long productId = productDao.save(product);

        assertDoesNotThrow(() -> cartService.add(customerId, new CartItemAddRequest(productId, 1)));
    }

    @DisplayName("카트에 재고보다 많은 수량 추가시 예외 발생")
    @Test
    void add_quantityOverStock_throwsException() {
        Long customerId = customerDao.save(customer);
        Long productId = productDao.save(product);

        assertThatThrownBy(() -> cartService.add(customerId, new CartItemAddRequest(productId, 11)));
    }

    @DisplayName("카트 아이템 수량 수정")
    @Test
    void updateQuantity() {
        Long customerId = customerDao.save(customer);
        Long productId = productDao.save(product);
        Product savedProduct = productDao.findProductById(productId);
        Long cartItemId = cartItemDao.save(customerId, new CartItem(savedProduct, 1));

        cartService.updateQuantity(cartItemId, customerId, 2);
        CartItem result = cartItemDao.findById(cartItemId);

        assertThat(result.getQuantity()).isEqualTo(2);
    }

}
