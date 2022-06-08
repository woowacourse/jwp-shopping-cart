package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.application.dto.OrderRequestDto;
import woowacourse.shoppingcart.dao.*;
import woowacourse.shoppingcart.domain.*;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.util.PasswordEncryptor;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private CartItemDao cartItemDao;

    @DisplayName("주문을 추기한다.")
    @Test
    void addOrder() {
        // given
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));
        final OrderRequestDto orderRequestDto = new OrderRequestDto(1L, 10);

        // when
        when(customerDao.findById(any(Long.class)))
                .thenReturn(Optional.of(customer));
        when(orderDao.addOrders(any(Long.class)))
                .thenReturn(1L);
        when(cartItemDao.findProductIdById(any(Long.class)))
                .thenReturn(1L);
        when(ordersDetailDao.addOrdersDetail(any(Long.class), any(Long.class), any(Integer.class)))
                .thenReturn(any(Long.class));

        // then
        assertThatCode(() -> orderService.addOrder(customer.getId(), List.of(orderRequestDto)))
                .doesNotThrowAnyException();
    }

    @DisplayName("주문 추가 시 회원이 존재하지 않는다면 예외가 발생한다.")
    @Test
    void addOrder_customerNotExist_throwsException() {
        // given
        final OrderRequestDto orderRequestDto = new OrderRequestDto(1L, 10);

        // when
        when(customerDao.findById(1L))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> orderService.addOrder(1L, List.of(orderRequestDto)))
                .isInstanceOf(CustomerNotFoundException.class);

    }

    @DisplayName("단일 주문에 대한 모든 내역을 불러온다.")
    @Test
    void findOrderById() {
        // given
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));
        final OrderDetail orderDetail = new OrderDetail(1L, 10);

        // when
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(customer));
        when(orderDao.isValidOrderId(any(Long.class), any(Long.class)))
                .thenReturn(true);
        when(ordersDetailDao.findAllByOrderId(1L))
                .thenReturn(List.of(orderDetail));

        // then
        assertThat(orderService.findOrderById(1L, 1L))
                .isInstanceOf(OrderResponse.class);
    }

    @DisplayName("고객의 모든 주문에 대한 내역을 불러온다.")
    @Test
    void findOrdersByCustomerId() {
        // given
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));
        final OrderDetail orderDetail = new OrderDetail(1L, 1000, "sun", "url", 10);
        final Orders orders = new Orders(1L, List.of(orderDetail));

        // when
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(customer));
        when(orderDao.findOrderIdsByCustomerId(1L))
                .thenReturn(List.of(1L));
        when(orderDao.existsOrderByCustomerId(1L))
                .thenReturn(true);
        when(ordersDetailDao.findAllByOrderId(1L))
                .thenReturn(List.of(orderDetail));

        // then
        assertThat(orderService.findOrdersByCustomerId(1L)).usingRecursiveComparison()
                .isEqualTo(List.of(OrderResponse.from(orders)));
    }
}
