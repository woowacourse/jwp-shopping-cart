package woowacourse.cartitem.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.cartitem.dao.CartItemDao;
import woowacourse.cartitem.domain.CartItem;
import woowacourse.cartitem.domain.Quantity;
import woowacourse.cartitem.dto.CartItemAddRequest;
import woowacourse.cartitem.dto.CartItemResponse;
import woowacourse.cartitem.dto.CartItemResponses;
import woowacourse.customer.application.CustomerService;
import woowacourse.product.dao.ProductDao;
import woowacourse.product.domain.Product;
import woowacourse.product.exception.InvalidProductException;
import woowacourse.cartitem.exception.InvalidCartItemException;

@Transactional(rollbackFor = Exception.class)
@Service
public class CartItemService {

    private final CustomerService customerService;
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public CartItemService(
        final CustomerService customerService,
        final ProductDao productDao,
        final CartItemDao cartItemDao
    ) {
        this.customerService = customerService;
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    public Long addCartItem(final String username, final CartItemAddRequest cartItemAddRequest) {
        final Long customerId = customerService.findCustomerIdByUsername(username);
        validateProductStock(cartItemAddRequest.getProductId(), cartItemAddRequest.getQuantity());
        final Quantity quantity = new Quantity(cartItemAddRequest.getQuantity());

        return cartItemDao.save(customerId, cartItemAddRequest.getProductId(), quantity.getValue());
    }

    @Transactional(readOnly = true)
    public CartItemResponses findCartItemsByCustomerName(final String customerName) {
        final Long customerId = customerService.findCustomerIdByUsername(customerName);
        final List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customerId);

        return CartItemResponses.from(cartItems);
    }

    @Transactional(readOnly = true)
    public CartItemResponse findCartItemById(final Long cartItemId) {
        final CartItem cartItem = cartItemDao.findById(cartItemId)
            .orElseThrow(() -> new InvalidCartItemException("장바구니를 찾을 수 없습니다."));

        return CartItemResponse.from(cartItem);
    }

    public void updateQuantity(final String customerName, final Long cartItemId, final int quantity) {
        validateCustomerCart(cartItemId, customerName);
        final CartItem cartItem = cartItemDao.findById(cartItemId)
            .orElseThrow(() -> new InvalidCartItemException("장바구니를 찾을 수 없습니다."));

        validateProductStock(cartItem.getProductId(), quantity);
        cartItem.updateQuantity(quantity);

        cartItemDao.update(cartItemId, cartItem);
    }

    private void validateProductStock(final Long productId, final Integer quantity) {
        final Product product = productDao.findById(productId)
            .orElseThrow(() -> new InvalidProductException("존재하지 않는 상품입니다."));
        product.checkProductAvailableForPurchase(quantity);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final Long customerId = customerService.findCustomerIdByUsername(customerName);
        final List<Long> cartIds = cartItemDao.findAllIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new InvalidCartItemException();
    }

    public void deleteCartItem(final String customerName, final Long cartId) {
        final Long customerId = customerService.findCustomerIdByUsername(customerName);
        cartItemDao.deleteByIdAndCustomerId(cartId, customerId);
    }
}
