package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static woowacourse.fixture.PasswordFixture.encryptedBasicPassword;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.fixture.ProductFixture;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.response.OrderDetailResponse;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrdersDetailDao ordersDetailDao;

    @Mock
    private CustomerDao customerDao;

    @DisplayName("주문 id를 통해서 주문한 항목을 조회한다.")
    @Test
    void findOrderById() {
        // given
        Long customerId = 1L;
        Long ordersId = 1L;
        Customer customer = new Customer(customerId, new UserName("giron"), encryptedBasicPassword);
        given(customerDao.findById(customerId))
                .willReturn(Optional.of(customer));
        given(orderDao.isValidOrderId(customerId, ordersId))
                .willReturn(true);
        given(ordersDetailDao.findOrdersDetailsJoinProductByOrderId(ordersId))
                .willReturn(List.of(
                        new OrderDetail(1, ProductFixture.PRODUCT_BANANA),
                        new OrderDetail(2, ProductFixture.PRODUCT_APPLE)
                ));

        // when
        final OrdersResponse ordersResponse = orderService.findOrderById(customerId, ordersId);
        final List<String> productNames = ordersResponse.getOrderDetails().stream()
                .map(OrderDetailResponse::getName)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(ordersResponse.getId()).isEqualTo(ordersId),
                () -> assertThat(productNames).containsExactly("banana", "apple"),
                () -> verify(customerDao).findById(customerId),
                () -> verify(orderDao).isValidOrderId(customerId, ordersId),
                () -> verify(ordersDetailDao).findOrdersDetailsJoinProductByOrderId(ordersId)
        );
    }

    @DisplayName("유저의 id를 통해서 주문한 모든 항목을 조회한다.")
    @Test
    void findOrdersByCustomerId() {
        // given
        Long customerId = 1L;
        Long ordersId = 1L;
        Customer customer = new Customer(customerId, new UserName("giron"), encryptedBasicPassword);
        given(customerDao.findById(customerId))
                .willReturn(Optional.of(customer));
        given(orderDao.findOrderIdsByCustomerId(customerId))
                .willReturn(List.of(ordersId));
        given(ordersDetailDao.findOrdersDetailsJoinProductByOrderId(ordersId))
                .willReturn(List.of(
                        new OrderDetail(1, ProductFixture.PRODUCT_BANANA),
                        new OrderDetail(2, ProductFixture.PRODUCT_APPLE)
                ));

        // when
        final List<OrdersResponse> ordersResponses = orderService.findOrdersByCustomerId(customerId);
        final List<Long> ordersIds = ordersResponses.stream()
                .map(OrdersResponse::getId)
                .collect(Collectors.toList());
        final List<String> productNames = ordersResponses.stream()
                .map(OrdersResponse::getOrderDetails)
                .flatMap(List::stream)
                .map(OrderDetailResponse::getName)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(ordersIds).containsExactly(ordersId),
                () -> assertThat(productNames).containsExactly("banana", "apple"),
                () -> verify(customerDao).findById(customerId),
                () -> verify(orderDao).findOrderIdsByCustomerId(customerId),
                () -> verify(ordersDetailDao).findOrdersDetailsJoinProductByOrderId(ordersId)
        );
    }
}
