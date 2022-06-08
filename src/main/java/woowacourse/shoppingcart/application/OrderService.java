package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.exception.InvalidOrderException;
import woowacourse.shoppingcart.dao.*;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Orders;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final CartService cartService;

    public Orders order(Long customerId, List<Long> productIds) {
        List<CartItem> cartItems = cartService.findItemInCart(customerId, productIds);
        cartService.deleteItems(customerId, productIds);
        return orderDao.save(new Orders(customerId, cartItems));
    }

	public Orders findOne(Long orderId, Long customerId) {
		Orders order = orderDao.findById(orderId);
		validateOrderOfCustomer(customerId, order);
		return order;
	}

	private void validateOrderOfCustomer(Long customerId, Orders order) {
		if (!order.isSameCustomerId(customerId)) {
			throw new InvalidOrderException("존재하지 않는 주문입니다.");
		}
	}
}
