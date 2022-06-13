package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.exception.InvalidOrderException;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	private final Long customerId = 1L;
	private final List<Long> productIds = List.of(1L, 2L, 3L);

	@Mock
	private OrderDao orderDao;
	@Mock
	private CartService cartService;
	@InjectMocks
	private OrderService orderService;

	@DisplayName("주문을 생성한다.")
	@Test
	void order() {
		// given
		List<CartItem> cartItems = List.of(
			new CartItem(1L, new Product(1L, "치킨", 20000, "https://test.jpg"), 2),
			new CartItem(2L, new Product(2L, "콜라", 1500, "https://test.jpg"), 2),
			new CartItem(3L, new Product(3L, "피자", 15000, "https://test.jpg"), 2)
		);
		given(cartService.findItemsByProductIdsInCart(customerId, productIds))
			.willReturn(cartItems);
		Orders order = new Orders(customerId, cartItems);
		given(orderDao.save(order))
			.willReturn(order.createWithId(1L));

		// when
		Orders resultOrder = orderService.order(customerId, productIds);

		// then
		assertAll(
			() -> assertThat(resultOrder).isEqualTo(order),
			() -> verify(cartService).findItemsByProductIdsInCart(customerId, productIds),
			() -> verify(orderDao).save(order)
		);
	}

	@DisplayName("주문을 하나 조회한다.")
	@Test
	void findOrder() {
		// given
		List<OrderDetail> orderDetails = List.of(
			new OrderDetail(new Product(1L, "치킨", 20000, "https://test.jpg"), 2),
			new OrderDetail(new Product(2L, "콜라", 1500, "https://test.jpg"), 2),
			new OrderDetail(new Product(3L, "피자", 15000, "https://test.jpg"), 2)
		);
		long customerId = 1L;
		long orderId = 1L;
		given(orderDao.findById(customerId))
			.willReturn(new Orders(orderId, customerId, orderDetails, LocalDateTime.now()));

		// when
		orderService.findOne(orderId, customerId);

		// then
		verify(orderDao).findById(orderId);
	}

	@DisplayName("회원 id와 주문의 회원 id가 다르면 조회하지 못한다.")
	@Test
	void findOrderException() {
		// given
		List<OrderDetail> orderDetails = List.of(
			new OrderDetail(new Product(1L, "치킨", 20000, "https://test.jpg"), 2),
			new OrderDetail(new Product(2L, "콜라", 1500, "https://test.jpg"), 2),
			new OrderDetail(new Product(3L, "피자", 15000, "https://test.jpg"), 2)
		);
		long customerId = 1L;
		long orderId = 1L;
		given(orderDao.findById(customerId))
			.willReturn(new Orders(orderId, customerId, orderDetails, LocalDateTime.now()));

		// when
		assertThatThrownBy(() -> orderService.findOne(orderId, 2L))
			.isInstanceOf(InvalidOrderException.class);

		// then
		verify(orderDao).findById(orderId);
	}
}
