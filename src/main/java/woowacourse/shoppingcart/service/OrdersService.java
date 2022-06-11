package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrdersDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.OrdersDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.OrdersDetailDto;
import woowacourse.shoppingcart.dto.OrdersDetailProductResponseDto;
import woowacourse.shoppingcart.dto.OrdersResponseDto;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.NotFoundOrdersException;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersService {

    private final OrdersDao ordersDao;
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public OrdersService(final OrdersDao ordersDao, final ProductDao productDao, final CartItemDao cartItemDao) {
        this.ordersDao = ordersDao;
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    public Long order(final List<Long> productIds, final Long customerId) {
        final List<CartItem> orderCartItems
                = cartItemDao.findCartItemsByProductIdsAndCustomerId(productIds, customerId);
        checkProductsInCartItems(productIds, orderCartItems);
        orderCartItems.forEach(this::decreaseProductQuantity);
        final List<OrdersDetail> ordersDetails = orderCartItems.stream()
                .map(this::convertCartItemToOrdersDetails)
                .collect(Collectors.toList());

        cartItemDao.deleteCartItems(productIds, customerId);

        final Orders orders = Orders.createWithoutId(customerId, ordersDetails);
        return ordersDao.save(orders);
    }

    private void checkProductsInCartItems(final List<Long> productIds, final List<CartItem> orderCartItems) {
        if (productIds.size() != orderCartItems.size()) {
            throw new InvalidOrderException();
        }
    }

    private void decreaseProductQuantity(final CartItem cartItem) {
        final Product product = productDao.findProductById(cartItem.getProductId())
                .orElseThrow(NotFoundProductException::new);
        product.decreaseQuantity(cartItem.getCount());
        productDao.updateQuantity(cartItem.getProductId(), product.getQuantity());
        cartItemDao.updateCartItemCount(cartItem.getProductId(), product.getQuantity());
    }

    private OrdersDetail convertCartItemToOrdersDetails(final CartItem cartItem) {
        return OrdersDetail.createWithoutId(
                productDao.findProductById(cartItem.getProductId())
                        .orElseThrow(NotFoundProductException::new),
                cartItem.getCount()
        );
    }

    public OrdersResponseDto findOrders(final Long ordersId) {
        final Orders orders = ordersDao.findById(ordersId)
                .orElseThrow(NotFoundOrdersException::new);

        return convertOrdersToDto(orders);
    }

    private OrdersDetailDto convertOrdersDetailToDto(final OrdersDetail ordersDetail) {
        final Product product = ordersDetail.getProduct();
        final OrdersDetailProductResponseDto productResponseDto = new OrdersDetailProductResponseDto(
                product.getId(),
                product.getThumbnailUrl(),
                product.getName(),
                product.getPrice()
        );
        return new OrdersDetailDto(productResponseDto, ordersDetail.getCount());
    }

    public List<OrdersResponseDto> findCustomerOrders(final Long customerId) {
        final List<Orders> orders = ordersDao.findOrdersByCustomerId(customerId);
        return orders.stream()
                .map(this::convertOrdersToDto)
                .collect(Collectors.toList());
    }

    private OrdersResponseDto convertOrdersToDto(final Orders orders) {
        final List<OrdersDetailDto> ordersDetailDtos = orders.getOrdersDetails()
                .stream()
                .map(this::convertOrdersDetailToDto)
                .collect(Collectors.toList());
        return new OrdersResponseDto(ordersDetailDtos);
    }
}
