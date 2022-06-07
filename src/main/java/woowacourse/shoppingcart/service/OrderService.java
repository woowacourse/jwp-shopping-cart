package woowacourse.shoppingcart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.OrderRepository;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.cart.CartItemRepository;
import woowacourse.shoppingcart.domain.cart.CartItems;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.NotMyCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final CustomerDao customerDao;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(CustomerDao customerDao, OrderRepository orderRepository,
        CartItemRepository cartItemRepository) {
        this.customerDao = customerDao;
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public long addOrder(String email, OrderRequest orderRequest) {
        long customerId = findCustomerIdByEmail(email);
        List<Long> cartItemIds = orderRequest.getCartItemIds();
        List<OrderDetail> orderDetails = new ArrayList<>();

        CartItems cartItems = cartItemRepository.findByCustomer(customerId);
        for (Long cartItemId : cartItemIds) {
            CartItem cartItem = cartItemRepository.findById(cartItemId);
            if (!cartItems.contains(cartItem)) {
                throw new NotMyCartItemException();
            }
            cartItemRepository.delete(cartItem);
            orderDetails.add(new OrderDetail(cartItem.getProduct(), new Quantity(cartItem.getQuantity())));
        }

        return orderRepository.add(customerId, new Orders(orderDetails));
    }

    public List<OrderResponse> findOrdersByCustomer(String email) {
        long customerId = findCustomerIdByEmail(email);
        return orderRepository.findOrders(customerId).stream()
            .map(OrderResponse::from)
            .collect(Collectors.toList());
    }

    // TODO: 중복 제거 뺼 방법 고려
    private long findCustomerIdByEmail(String email) {
        Customer customer = customerDao.findByEmail(email);
        return customer.getId();
    }
}
