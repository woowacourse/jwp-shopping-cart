package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartsDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.OrdersDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartsDao cartsDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartsDao cartsDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartsDao = cartsDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long addOrder(final Long memberId, final List<OrderRequest> orderRequests) {
        final Long orderId = orderDao.save(memberId);
        final List<Long> cartIds = orderRequests.stream()
                .map(OrderRequest::getCartId)
                .collect(Collectors.toList());
        final List<Carts> carts = cartsDao.findCartsByIds(cartIds);
        ordersDetailDao.addBatchOrderDetails(toOrderDetails(orderId, carts));

        return orderId;
    }

    private List<OrderDetail> toOrderDetails(final Long orderId, final List<Carts> carts) {
        return carts.stream()
                .map(cart -> new OrderDetail(orderId, cart.getProduct(), cart.getQuantity()))
                .collect(Collectors.toList());
    }

    public OrderResponse findOrderById(final Long memberId, final Long orderId) {
        validateOrderIdByMemberId(memberId, orderId);
        final List<OrderDetail> orderDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        return new OrderResponse(orderId, toOrderDetailResponses(orderDetails));
    }

    private List<OrderDetailResponse> toOrderDetailResponses(final List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toList());
    }

    private void validateOrderIdByMemberId(final Long memberId, final Long orderId) {
        if (!orderDao.isValidOrderId(memberId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    private Orders findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrdersDetail> ordersDetails = new ArrayList<>();
        for (final OrderDetail productQuantity : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productDao.findProductById(productQuantity.getProduct().getId());
            final int quantity = productQuantity.getQuantity();
            ordersDetails.add(new OrdersDetail(product, quantity));
        }

        return new Orders(orderId, ordersDetails);
    }
}
