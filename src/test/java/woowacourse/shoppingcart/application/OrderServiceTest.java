package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.*;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.OrderNotFoundException;
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
        final OrderRequest orderRequest = new OrderRequest(1L, 10);

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
        assertThatCode(() -> orderService.addOrder(customer.getId(), List.of(orderRequest)))
                .doesNotThrowAnyException();
    }

    @DisplayName("주문 추가 시 회원이 존재하지 않는다면 예외가 발생한다.")
    @Test
    void addOrder_customerNotExist_throwsException() {
        // given
        final OrderRequest orderRequest = new OrderRequest(1L, 10);

        // when
        when(customerDao.findById(1L))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> orderService.addOrder(1L, List.of(orderRequest)))
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

    @DisplayName("단일 주문 내역 조회시 존재하지 않는 주문이라면 예외가 발생한다.")
    @Test
    void findOrderById_customerNotExist_throwsException() {
        // given
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));
        final OrderDetail orderDetail = new OrderDetail(1L, 10);

        // when
        when(customerDao.findById(1L))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> orderService.findOrderById(1L, 1L))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @DisplayName("단일 주문 내역 조회시 존재하지 않는 주문이라면 예외가 발생한다.")
    @Test
    void findOrderById_orderNotExist_throwsException() {
        // given
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));
        final OrderDetail orderDetail = new OrderDetail(1L, 10);

        // when
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(customer));
        when(orderDao.isValidOrderId(any(Long.class), any(Long.class)))
                .thenReturn(false);

        // then
        assertThatThrownBy(() -> orderService.findOrderById(1L, 1L))
                .isInstanceOf(InvalidOrderException.class)
                .hasMessage("주문 내역이 존재하지 않습니다.");
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

    @DisplayName("주문 내역이 존재하지 않을 때 고객의 모든 주문에 대한 내역을 불러올 경우 예외가 발생한다.")
    @Test
    void findOrdersByCustomerId_orderNotExists_throwsException() {
        // given
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));

        // when
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(customer));
        when(orderDao.existsOrderByCustomerId(1L))
                .thenReturn(false);

        // then
        assertThatThrownBy(() -> orderService.findOrdersByCustomerId(1L))
                .isInstanceOf(OrderNotFoundException.class);
    }
}
