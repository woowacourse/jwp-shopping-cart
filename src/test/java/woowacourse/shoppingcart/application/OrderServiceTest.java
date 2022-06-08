package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.ui.dto.OrderDetailRequest;
import woowacourse.support.test.ExtendedApplicationTest;

@ExtendedApplicationTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProductDao productDao;

    private Long customerId;
    private Long cartId;
    private Long productId;

    @BeforeEach
    void setUp() {
        productId = productDao.save(new Product("productname", 1000, "http://example.com", "some-description"));
        customerId = customerDao.save(
                Customer.fromInput("username", "password1234!", "example@email.com", "some-address", "010-0123-1234"))
            .orElseThrow();

        cartId = cartItemDao.addCartItem(customerId, productId, 10);
    }

    @DisplayName("회원이 주문한다.")
    @Test
    void addOrder() {
        // given
        final List<OrderDetailRequest> orderDetailRequests = List.of(new OrderDetailRequest(cartId, 5));
        // when
        final Long orderId = orderService.addOrder(orderDetailRequests, customerId);
        // then
        assertThat(orderId).isNotNull();
    }

    @DisplayName("주문을 조회한다.")
    @Test
    void findOrderById() {
        // given
        final List<OrderDetailRequest> orderDetailRequests = List.of(new OrderDetailRequest(cartId, 5));
        final Long orderId = orderService.addOrder(orderDetailRequests, customerId);

        // when
        final OrderResponse orderResponse = orderService.findOrderById(customerId, orderId);
        // then
        assertThat(orderResponse.getOrderDetails()).hasSize(1);
    }

    @DisplayName("주문 목록을 조회한다.")
    @Test
    void findOrdersByCustomerId() {
        // given
        final List<OrderDetailRequest> orderDetailRequests = List.of(new OrderDetailRequest(cartId, 5));
        final Long orderId = orderService.addOrder(orderDetailRequests, customerId);
        // when
        final List<OrderResponse> orderResponses = orderService.findOrdersByCustomerId(customerId);
        // then
        Assertions.assertAll(
            () -> assertThat(orderResponses).hasSize(1),
            () -> assertThat(orderResponses.get(0).getOrderDetails()).hasSize(1)
        );
    }
}