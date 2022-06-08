package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Orders;

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
			new CartItem(1L, 1L, "치킨", 20000, "test.jpg", 2),
			new CartItem(2L, 2L, "콜라", 1500, "test.jpg", 2),
			new CartItem(3L, 3L, "피자", 15000, "test.jpg", 2)
		);
		given(cartService.findItemInCart(customerId, productIds))
			.willReturn(cartItems);
		Orders order = new Orders(customerId, cartItems);
		given(orderDao.save(order))
			.willReturn(order.createWithId(1L));

		// when
		Orders resultOrder = orderService.order(customerId, productIds);

		// then
		assertAll(
			() -> assertThat(resultOrder).isEqualTo(order),
			() -> verify(cartService).findItemInCart(customerId, productIds),
			() -> verify(orderDao).save(order)
		);
	}
}
