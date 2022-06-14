package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.common.exception.NotFoundException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.OrderResponse;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.repository.CustomerRepository;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderDao orderDao,
            OrdersDetailDao ordersDetailDao,
            CartItemDao cartItemDao,
            ProductRepository productRepository,
            CustomerRepository customerRepository) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public Long addOrder(List<OrderRequest> orderDetailRequests, Long customerId) {
        Customer customer = getCustomer(customerId);
        Long ordersId = orderDao.addOrders(customer.getId());

        for (OrderRequest orderDetail : orderDetailRequests) {
            Long cartId = orderDetail.getCartId();
            Long productId = cartItemDao.findProductIdById(cartId);
            int quantity = orderDetail.getQuantity();

            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteCartItem(cartId);
        }

        return ordersId;
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }

    public OrderResponse findOrderById(Long customerId, Long orderId) {
        validateOrderIdByCustomerId(customerId, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerId(Long customerId, Long orderId) {
        Customer customer = getCustomer(customerId);

        if (!orderDao.isValidOrderId(customer.getId(), orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<OrderResponse> findOrdersByCustomerName(Long customerId) {
        Customer customer = getCustomer(customerId);
        List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customer.getId());

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
    }

    private OrderResponse findOrderResponseDtoByOrderId(Long orderId) {
        final List<OrderDetail> ordersDetails = new ArrayList<>();
        for (OrderDetail orderDetail : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            Product product = getProduct(orderDetail);
            int quantity = orderDetail.getQuantity();
            ordersDetails.add(new OrderDetail(product, quantity));
        }

        return new OrderResponse(orderId, ordersDetails);
    }

    private Product getProduct(OrderDetail orderDetail) {
        return productRepository.findById(orderDetail.getId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 상품입니다."));
    }
}
