package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderDetailDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.exception.InvalidOrderException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static woowacourse.shoppingcart.application.ProductServiceTest.상품1;
import static woowacourse.shoppingcart.application.ProductServiceTest.상품2;

class OrderServiceTest {

    public static final OrderDetail 주문_상품1 = new OrderDetail(상품1, 1);
    public static final OrderDetail 주문_상품2 = new OrderDetail(상품2, 3);

    private final OrderService orderService;

    @Mock
    private OrderDao orderDao;
    @Mock
    private OrderDetailDao orderDetailDao;
    @Mock
    private CartItemDao cartItemDao;

    OrderServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.orderService = new OrderService(orderDao, orderDetailDao, cartItemDao);
    }

    @DisplayName("주문을 추가한다.")
    @Test
    void addOrder() {
        // given
        given(orderDao.addOrders(1L)).willReturn(1L);
        given(cartItemDao.findIdByCustomerIdAndProductId(1L, 1L)).willReturn(1L);
        given(cartItemDao.findIdByCustomerIdAndProductId(1L, 2L)).willReturn(2L);
        // when
        final OrderRequest ordersRequest = new OrderRequest(
                List.of(new OrderDetailRequest(1L, 1),
                        new OrderDetailRequest(2L, 2))
        );
        final long orderId = orderService.addOrder(ordersRequest, 1L);
        // then
        assertThat(orderId).isEqualTo(1L);
    }

    @DisplayName("orderId로 주문을 조회한다.")
    @Test
    void findOrderById() {
        // given
        final List<OrderDetail> order = List.of(주문_상품1, 주문_상품2);
        given(orderDao.findOrderIdByOrderIdAndCustomerId(1L, 1L)).willReturn(Optional.of(1L));
        given(orderDetailDao.findOrderDetailsByOrderId(1L)).willReturn(order);

        // when
        final OrderResponse orderResponse = orderService.findOrderById(1L, 1L);
        final OrderResponse expected = new OrderResponse(
                1L,
                List.of(new OrderDetailResponse(1L, "상품1", 1_000, 1, "imageUrl1"),
                        new OrderDetailResponse(2L, "상품2", 6_000, 3, "imageUrl2")),
                7_000
        );
        // then
        assertThat(orderResponse).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("사용자가 주문하지 않은 orderId로 주문을 조회하면 예외를 발생한다.")
    @Test
    void throwWhenOrderNotExist() {
        // given
        given(orderDao.findOrderIdByOrderIdAndCustomerId(1L, 1L)).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> orderService.findOrderById(1L, 1L))
                .isInstanceOf(InvalidOrderException.class)
                .hasMessage("유저에게는 해당 order_id가 없습니다.");
    }


    @DisplayName("사용자 id로 주문 목록을 조회한다.")
    @Test
    void findOrdersByCustomerId() {
        // given
        given(orderDao.findOrderIdsByCustomerId(1L)).willReturn(List.of(1L));
        given(orderDetailDao.findOrderDetailsByOrderId(1L)).willReturn(List.of(주문_상품1, 주문_상품2));
        // when
        final OrdersResponse ordersResponse = orderService.findOrdersByCustomerId(1L);
        final OrdersResponse expected = new OrdersResponse(
                List.of(
                        new OrderResponse(1L,
                                List.of(
                                        new OrderDetailResponse(1L, "상품1", 1_000, 1, "imageUrl1"),
                                        new OrderDetailResponse(2L, "상품2", 6_000, 3, "imageUrl2")
                                ),
                                7_000)
                )
        );
        // then
        assertThat(ordersResponse).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
