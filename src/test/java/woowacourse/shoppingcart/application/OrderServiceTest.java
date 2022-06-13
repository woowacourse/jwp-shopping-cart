package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
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
import woowacourse.shoppingcart.dto.OrderRequest;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

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

    private Product beer = Product.builder()
            .productName("beer")
            .price(3_000)
            .stock(10)
            .build();

    @DisplayName("주문을 저장한다.")
    @Test
    void save() {
        Long customerId = customerDao.save(customer);
        Long productId = productDao.save(beer);
        Product product = productDao.findProductById(productId);
        CartItem cartItem = new CartItem(product, 1);
        Long cartItemId = cartItemDao.save(customerId, cartItem);

        Long orderId = orderService.save(List.of(new OrderRequest(cartItemId)), customerId);
        assertThat(orderId).isNotNull();
    }

}
