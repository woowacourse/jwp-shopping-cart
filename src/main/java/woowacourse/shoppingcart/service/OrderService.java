package woowacourse.shoppingcart.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartItemIds;
import woowacourse.shoppingcart.dto.OrdersDto;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.entity.OrderDetailEntity;
import woowacourse.shoppingcart.exception.NotExistOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final ProductService productService;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
            final CartItemDao cartItemDao, final CustomerDao customerDao,
            final ProductDao productDao,
            final ProductService productService) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
        this.productService = productService;
    }

    public Long addOrder(final Customer customer, final CartItemIds cartItemIds) {
        final Long customerId = customer.getId();
        Long ordersId = orderDao.addOrders(customerId);
        for (final Long cartItemId : cartItemIds.getCartItemIds()) {
            CartItemEntity cartItemEntity = cartItemDao.findById(customerId, cartItemId);
            CartItem cartItem = toCartItem(cartItemEntity);
            cartItem.validateOutOfStock();
            ordersDetailDao.addOrdersDetail(ordersId, cartItem);
            cartItemDao.deleteCartItem(cartItem.getId());
        }

        return ordersId;
    }

    public List<OrdersDto> findOrdersByCustomer(final Customer customer) {
        List<OrdersDto> result = new LinkedList<>();
        List<Long> orderIds = orderDao.findByCustomerId(customer.getId());
        for (Long orderId : orderIds) {
            List<OrderDetail> orderDetails = getOrderDetails(orderId);
            result.add(OrdersDto.of(orderId, orderDetails));
        }

        return result;
    }

    public OrdersDto findOrderByCustomerAndOrderId(Customer customer, Long orderId) {
        validateExistOrder(customer, orderId);
        List<OrderDetail> orderDetails = getOrderDetails(orderId);
        return OrdersDto.of(orderId, orderDetails);
    }

    private void validateExistOrder(Customer customer, Long orderId) {
        final boolean result = orderDao.isValidOrderId(customer.getId(), orderId);
        if (!result) {
            throw new NotExistOrderException("주문 번호가 없습니다.", ErrorResponse.NOT_EXIST_ORDER);
        }
    }

    private List<OrderDetail> getOrderDetails(Long orderId) {
        List<OrderDetail> orderDetails = new LinkedList<>();
        List<OrderDetailEntity> entities = ordersDetailDao.findByOrderId(orderId);
        for (OrderDetailEntity entity : entities) {
            Product product = productDao.findProductById(entity.getProduct_id());
            OrderDetail orderDetail = new OrderDetail(entity.getId(), product, entity.getQuantity());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    private CartItem toCartItem(CartItemEntity entity) {
        return new CartItem(entity.getId(),
                productDao.findProductById(entity.getProductId()),
                entity.getQuantity());
    }
}
