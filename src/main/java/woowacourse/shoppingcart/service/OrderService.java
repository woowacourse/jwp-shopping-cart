package woowacourse.shoppingcart.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.OrdersDto;
import woowacourse.shoppingcart.entity.OrderDetailEntity;
import woowacourse.shoppingcart.exception.NotExistException;

@Service
@Transactional
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final ProductService productService;
    private final CustomerService customerService;
    private final CartItemService cartItemService;

    public OrderService(OrderDao orderDao, OrdersDetailDao ordersDetailDao,
            ProductService productService,
            CustomerService customerService,
            CartItemService cartItemService) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.productService = productService;
        this.customerService = customerService;
        this.cartItemService = cartItemService;
    }

    public Long addOrder(final Customer customer, final List<Long> cartItemIds) {
        final Long customerId = customer.getId();
        Long ordersId = orderDao.addOrders(customerId);

        for (final Long cartItemId : cartItemIds) {
            CartItem cartItem = cartItemService.findById(customer, cartItemId);
            cartItem.validateOutOfStock();

            ordersDetailDao.addOrdersDetail(ordersId, cartItem);
            cartItemService.deleteById(cartItemId);

            Product product = cartItem.getProduct();
            productService.updateQuantity(product.getId(), product.getStockQuantity() - cartItem.getQuantity());
        }

        return ordersId;
    }

    @Transactional(readOnly = true)
    public List<OrdersDto> findOrders(final Customer customer) {
        List<OrdersDto> result = new LinkedList<>();
        List<Long> orderIds = orderDao.findByCustomerId(customer.getId());
        for (Long orderId : orderIds) {
            List<OrderDetail> orderDetails = getOrderDetails(orderId);
            result.add(OrdersDto.of(orderId, orderDetails));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public OrdersDto findOrderDetails(Customer customer, Long orderId) {
        validateExistOrder(customer, orderId);
        List<OrderDetail> orderDetails = getOrderDetails(orderId);
        return OrdersDto.of(orderId, orderDetails);
    }

    private void validateExistOrder(Customer customer, Long orderId) {
        final boolean result = orderDao.isValidOrderId(customer.getId(), orderId);
        if (!result) {
            throw new NotExistException("주문 번호가 없습니다.", ErrorResponse.NOT_EXIST_ORDER);
        }
    }

    private List<OrderDetail> getOrderDetails(Long orderId) {
        List<OrderDetail> orderDetails = new LinkedList<>();
        List<OrderDetailEntity> entities = ordersDetailDao.findByOrderId(orderId);
        for (OrderDetailEntity entity : entities) {
            Product product = productService.findById(entity.getProduct_id());
            OrderDetail orderDetail = new OrderDetail(entity.getId(), product,
                    entity.getQuantity());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }
}
