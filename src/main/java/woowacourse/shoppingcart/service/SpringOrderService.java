package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.badrequest.OrderInvalidException;
import woowacourse.exception.notfound.OrderNotFoundException;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.Order;
import woowacourse.shoppingcart.infra.CartRepository;
import woowacourse.shoppingcart.infra.OrderRepository;
import woowacourse.shoppingcart.ui.dto.request.OrderCreateRequest;

@Transactional(readOnly = true)
@Service
public class SpringOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public SpringOrderService(final OrderRepository orderRepository, final CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public long order(final long customerId, final List<OrderCreateRequest> orderCreateRequests) {
        final Carts carts = cartRepository.findCartsByCustomerId(customerId);
        List<Cart> orderingCarts = carts.getCartsByIds(getCartIds(orderCreateRequests));
        validateOrder(orderingCarts);

        final long orderId = orderRepository.save(orderingCarts);
        carts.emptyCarts(orderingCarts);
        cartRepository.saveCarts(carts);

        return orderId;
    }

    private void validateOrder(final List<Cart> orderingCarts) {
        if (orderingCarts.isEmpty()) {
            throw new OrderInvalidException();
        }
    }

    @Override
    public Order findByOrderId(final long orderId) {
        return orderRepository.findOrderById(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public List<Order> findOrdersByCustomerId(final long customerId) {
        return orderRepository.findOrdersByCustomerId(customerId);
    }

    private List<Long> getCartIds(final List<OrderCreateRequest> orderCreateRequests) {
        return orderCreateRequests.stream()
                .map(OrderCreateRequest::getCartId)
                .collect(Collectors.toList());
    }
}
