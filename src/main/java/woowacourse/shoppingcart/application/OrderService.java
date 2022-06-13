package woowacourse.shoppingcart.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.domain.order.OrderItem;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final CartService cartService;
    private final ProductService productService;
    private final OrderDao orderDao;

    public OrderService(CartService cartService, ProductService productService, OrderDao orderDao) {
        this.cartService = cartService;
        this.productService = productService;
        this.orderDao = orderDao;
    }

    public Long save(long customerId) {
        Cart cart = cartService.findCartByCustomerId(customerId);
        validateCart(cart);

        Order order = new Order(customerId, cart);
        Long orderId = orderDao.save(order);

        updateProductsQuantity(cart);
        cartService.deleteCart(customerId);
        return orderId;
    }

    private void updateProductsQuantity(Cart cart) {
        for (CartItem cartItem : cart.getCartItems()) {
            productService.updateProductQuantity(cartItem.getProductId(), cartItem.getQuantityAfterOrder());
        }
    }

    private void validateCart(Cart cart) {
        if (cart.isEmpty()) {
            throw new InvalidOrderException("해당 고객의 장바구니가 존재하지 않습니다.");
        }
    }

    public List<Order> findAll(long customerId) {
        return orderDao.findAllByCustomerId(customerId);
    }

    public Order findOne(long customerId, long orderId) {
        List<OrderItem> orderItems = orderDao.findOrderItemsByOrderId(orderId);
        return new Order(customerId, orderItems);
    }
}
