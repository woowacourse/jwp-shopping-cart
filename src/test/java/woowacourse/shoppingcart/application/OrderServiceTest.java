package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static woowacourse.utils.Fixture.email;
import static woowacourse.utils.Fixture.치킨;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.dto.order.FindOrderResponse;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CartDao cartDao;
    @Mock
    private CustomerDao customerDao;
    @Mock
    private ProductDao productDao;
    @Mock
    private OrderDao orderDao;
    @Mock
    private OrdersDetailDao ordersDetailDao;
    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문번호로 주문을 조회한다.")
    void findOrderById() {
        // given
        OrderDetail orderDetail = new OrderDetail(1L, 1L, 치킨.getId(), 1000);
        given(ordersDetailDao.findOrdersDetailsByOrderId(any(Long.class)))
                .willReturn(List.of(orderDetail));
        given(productDao.findProductById(치킨.getId()))
                .willReturn(Optional.of(치킨));

        // when
        FindOrderResponse response = orderService.findOrderById(1L);

        // then
        assertAll(
                () -> assertThat(response.getTotalPrice()).isEqualTo(치킨.getPrice() * 1000),
                () -> assertThat(response.getId()).isEqualTo(1L),
                () -> assertThat(response.getOrderDetails()).isNotNull()
        );
    }

    @Test
    @DisplayName("주문을 추가한다.")
    void addOrder() {
        // given
        OrderDetail orderDetail = new OrderDetail(1L, 1L, 치킨.getId(), 1000);
        given(customerDao.findIdByEmail(email))
                .willReturn(1L);
        given(orderDao.addOrders(1L))
                .willReturn(1L);

        // when
        FindOrderResponse response = orderService.findOrderById(1L);

        // then
        assertAll(
                () -> assertThat(response.getTotalPrice()).isEqualTo(치킨.getPrice() * 1000),
                () -> assertThat(response.getId()).isEqualTo(1L),
                () -> assertThat(response.getOrderDetails()).isNotNull()
        );
    }
}
