package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.common.exception.NotFoundException;
import woowacourse.common.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderedProductDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderedProduct;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.order.OrderResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrderedProductDao orderedProductDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderService(final OrderDao orderDao, final OrderedProductDao orderedProductDao,
                        final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.orderDao = orderDao;
        this.orderedProductDao = orderedProductDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long addOrder(final List<Long> cartIds, final String email) {
        final Long customerId = customerDao.getIdByEmail(email);
        final Long ordersId = orderDao.save(customerId);

        for (Long cartId : cartIds) {
            CartItem cartItem = cartItemDao.getById(cartId);

            orderedProductDao.save(ordersId, cartItem);
            cartItemDao.delete(cartId);

            updateQuantity(cartItem);
        }
        return ordersId;
    }

    private void updateQuantity(CartItem cartItem) {
        cartItem.order();
        Product product = cartItem.getProduct();

        validateProduct(product);
        productDao.updateQuantity(product);
    }

    private void validateProduct(Product product) {
        if (!productDao.existsById(product.getId())) {
            throw new NotFoundException("존재하지 않는 상품입니다.", ErrorResponse.NOT_EXIST_PRODUCT);
        }
    }

    public OrderResponse findOrderById(final String email, final Long orderId) {
        validateOrderIdByCustomerEmail(email, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerEmail(final String email, final Long orderId) {
        final Long customerId = customerDao.getIdByEmail(email);

        if (!orderDao.existByOrderIdAndCustomerId(customerId, orderId)) {
            throw new NotFoundException("해당 주문은 존재하지 않습니다", ErrorResponse.NOT_EXIST_ORDER);
        }
    }

    private OrderResponse findOrderResponseDtoByOrderId(final Long orderId) {
        List<OrderedProduct> orderedProducts = orderedProductDao.getAllByOrderId(orderId);
        return new OrderResponse(orderId, orderedProducts);
    }

    public List<OrderResponse> findOrdersByCustomerEmail(final String email) {
        final Long customerId = customerDao.getIdByEmail(email);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toUnmodifiableList());
    }
}
