package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.domain.user.Customer;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Order;
import woowacourse.shoppingcart.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @Transactional
    public Long buyCartItems(Customer customer, List<Long> productIds) {
        Cart cart = cartService.findCart(customer);
        Order order = new Order(cart.extractCartItems(productIds));

        Long orderId = orderRepository.save(customer, order);
        cartService.removeCartItems(customer, order.getProductIds());
        return orderId;
    }
}
