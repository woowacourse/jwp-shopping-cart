package woowacourse.shoppingcart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.cart.CartItemRepository;
import woowacourse.shoppingcart.domain.cart.CartItems;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.order.OrderRepository;
import woowacourse.shoppingcart.domain.order.Orders;
import woowacourse.shoppingcart.domain.product.ProductStock;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(CustomerDao customerDao, ProductDao productDao,
        OrderRepository orderRepository,
        CartItemRepository cartItemRepository) {
        this.customerDao = customerDao;
        this.productDao = productDao;
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
            ProductStock productStock = productDao.findProductStockById(cartItem.getProductId());

            cartItems.checkContain(cartItem);
            cartItem.checkEnoughStockToOrder(productStock);

            productDao.update(productStock.reduce(cartItem));

            cartItemRepository.delete(cartItem);
            orderDetails.add(new OrderDetail(cartItem.getProduct(), new Quantity(cartItem.getQuantity())));
        }

        return orderRepository.add(customerId, new Orders(orderDetails));
    }

    @Transactional(readOnly = true)
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
