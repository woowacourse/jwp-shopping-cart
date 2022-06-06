package woowacourse.order.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.cartitem.application.CartItemService;
import woowacourse.cartitem.dto.CartItemAddRequest;
import woowacourse.customer.application.CustomerService;
import woowacourse.customer.dto.SignupRequest;
import woowacourse.order.dto.OrderAddRequest;
import woowacourse.product.application.ProductService;
import woowacourse.product.dto.ProductRequest;

@Transactional
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    private String username;
    private Long productId1;
    private Long productId2;
    private Long cartItemId1;
    private Long cartItemId2;

    @BeforeEach
    void setUp() {
        username = customerService.save(new SignupRequest("username", "password", "01000001111", "서울시"))
            .getUsername()
            .getValue();
        productId1 = productService.addProduct(new ProductRequest("짱구", 100_000_000, 10, "jjanggu.jpg"));
        productId2 = productService.addProduct(new ProductRequest("짱아", 10_000_000, 10, "jjanga.jpg"));
        cartItemId1 = cartItemService.addCartItem(username, new CartItemAddRequest(productId1, 5));
        cartItemId2 = cartItemService.addCartItem(username, new CartItemAddRequest(productId2, 5));
    }

    @DisplayName("주문을 추가한다.")
    @Test
    void addOrders() {
        final List<OrderAddRequest> orderRequests = Stream.of(cartItemId1, cartItemId2)
            .map(OrderAddRequest::new)
            .collect(Collectors.toList());

        final Long orderId = orderService.addOrder(username, orderRequests);

        assertThat(orderId).isNotNull();
    }
}
