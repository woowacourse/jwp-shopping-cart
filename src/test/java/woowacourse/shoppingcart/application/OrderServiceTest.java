package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
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
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrderDetailResponse;
import woowacourse.shoppingcart.dto.response.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrdersDetailDao ordersDetailDao;

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private CustomerDao customerDao;

    @DisplayName("유저의 id를 받아서 새로운 주문을 만들고, 주문할 아이템으로 주문의 상세사항을 저장한다. 저장된 주문사항은 장바구니에서 삭제한다.")
    @Test
    void addOrder() {
        // given
        Long customerId = 1L;
        Long ordersId = 1L;
        Long cartId = 1L;
        Customer customer = new Customer(customerId, new UserName("giron"), encryptedBasicPassword);
        Cart cart = new Cart(cartId, new Quantity(5), ProductFixture.PRODUCT_BANANA);
        given(customerDao.findById(customerId))
                .willReturn(Optional.of(customer));
        given(orderDao.addOrders(customerId))
                .willReturn(ordersId);
        given(cartItemDao.findJoinProductById(cartId))
                .willReturn(Optional.of(cart));
        willDoNothing().given(ordersDetailDao)
                .batchAddOrdersDetail(ordersId, List.of(cart));
        willDoNothing().given(cartItemDao)
                .batchDeleteCartItem(List.of(1L));

        // when
        final List<OrderRequest> orderRequests = List.of(new OrderRequest(cartId, 5));
        final Long addedOrderId = orderService.addOrder(orderRequests, customerId);

        // then
        assertAll(
                () -> assertThat(addedOrderId).isEqualTo(ordersId),
                () -> verify(customerDao).findById(customerId),
                () -> verify(orderDao).addOrders(customerId),
                () -> verify(cartItemDao).findJoinProductById(cartId),
                () -> verify(ordersDetailDao).batchAddOrdersDetail(ordersId, List.of(cart)),
                () -> verify(cartItemDao).batchDeleteCartItem(List.of(1L))
        );
    }

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

    @DisplayName("해당 유저가 주문하지 않은 주문 id로 주문한 항목을 조회하면 예외가 발생한다.")
    @Test
    void findOrderByIdWithWrongId() {
        // given
        Long customerId = 1L;
        Long ordersId = 1L;
        Customer customer = new Customer(customerId, new UserName("giron"), encryptedBasicPassword);
        given(customerDao.findById(customerId))
                .willReturn(Optional.of(customer));
        given(orderDao.isValidOrderId(customerId, ordersId))
                .willReturn(false);

        // when // then
        assertAll(
                () -> assertThatThrownBy(() -> orderService.findOrderById(customerId, ordersId))
                        .isExactlyInstanceOf(InvalidOrderException.class)
                        .hasMessageContaining("해당 유저가 주문하지 않은 항목입니다."),
                () -> verify(customerDao).findById(customerId),
                () -> verify(orderDao).isValidOrderId(customerId, ordersId)
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
