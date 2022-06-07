package woowacourse.shoppingcart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.NewOrderDetail;
import woowacourse.shoppingcart.domain.NewOrders;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.OrderRepository;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.cart.CartItemRepository;
import woowacourse.shoppingcart.domain.cart.CartItems;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.NewOrderRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.NotMyCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
        final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao,
        OrderRepository orderRepository, CartItemRepository cartItemRepository) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Long addOrder(final List<OrderRequest> orderDetailRequests, final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        final Long ordersId = orderDao.addOrders(customerId);

        for (final OrderRequest orderDetail : orderDetailRequests) {
            final Long cartId = orderDetail.getCartId();
            final Long productId = cartItemDao.findProductIdById(cartId);
            final int quantity = orderDetail.getQuantity();

            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteCartItem(cartId);
        }

        return ordersId;
    }

    public Orders findOrderById(final String customerName, final Long orderId) {
        validateOrderIdByCustomerName(customerName, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerName(final String customerName, final Long orderId) {
        final Long customerId = customerDao.findIdByUserName(customerName);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<Orders> findOrdersByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
            .map(this::findOrderResponseDtoByOrderId)
            .collect(Collectors.toList());
    }

    private Orders findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrderDetail> ordersDetails = new ArrayList<>();
        for (final OrderDetail productQuantity : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productDao.findProductById(productQuantity.getProductId());
            final int quantity = productQuantity.getQuantity();
            ordersDetails.add(new OrderDetail(product, quantity));
        }

        return new Orders(orderId, ordersDetails);
    }

    public long addOrder(String email, NewOrderRequest orderRequest) {
        long customerId = findCustomerIdByEmail(email);
        List<Long> cartItemIds = orderRequest.getCartItemIds();
        List<NewOrderDetail> orderDetails = new ArrayList<>();

        CartItems cartItems = cartItemRepository.findByCustomer(customerId);
        for (Long cartItemId : cartItemIds) {
            CartItem cartItem = cartItemRepository.findById(cartItemId);
            if (!cartItems.contains(cartItem)) {
                throw new NotMyCartItemException();
            }
            cartItemRepository.delete(cartItem);
            orderDetails.add(new NewOrderDetail(cartItem.getProduct(), new Quantity(cartItem.getQuantity())));
        }

        return orderRepository.add(customerId, new NewOrders(orderDetails));
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
