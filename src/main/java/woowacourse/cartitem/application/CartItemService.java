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

@Service
@Transactional(rollbackFor = Exception.class)
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

    public CartItemResponse addCartItem(final String username, final CartItemAddRequest cartItemAddRequest) {
        final Long customerId = customerService.findIdByUsername(username);
        validateProductStock(cartItemAddRequest.getProductId(), cartItemAddRequest.getQuantity());
        final Quantity quantity = new Quantity(cartItemAddRequest.getQuantity());

        final Long cartItemId = cartItemDao.addCartItem(customerId, cartItemAddRequest.getProductId(), quantity.getValue());
        final CartItem cartItem = cartItemDao.findCartItemById(cartItemId)
            .orElseThrow(() -> new woowacourse.cartitem.exception.InvalidCartItemException("장바구니를 찾을 수 없습니다."));

        return CartItemResponse.from(cartItem);
    }

    private void validateProductStock(final Long productId, final Integer quantity) {
        final Product product = productDao.findProductById(productId)
            .orElseThrow(() -> new InvalidProductException("해당 id에 따른 상품을 찾을 수 없습니다."));
        product.checkProductAvailableForPurchase(quantity);
    }

    public CartItemResponses findCartsByCustomerName(final String customerName) {
        final Long customerId = customerService.findIdByUsername(customerName);
        final List<CartItem> cartItems = cartItemDao.findByCustomerId(customerId);

        return CartItemResponses.from(cartItems);
    }

    public CartItemResponse findCartById(final Long cartItemId) {
        final CartItem cartItem = cartItemDao.findCartItemById(cartItemId)
            .orElseThrow(() -> new woowacourse.cartitem.exception.InvalidCartItemException("장바구니를 찾을 수 없습니다."));

        return CartItemResponse.from(cartItem);
    }

    public void updateQuantity(final String customerName, final Long cartItemId, final int quantity) {
        validateCustomerCart(cartItemId, customerName);
        final CartItem cartItem = cartItemDao.findCartItemById(cartItemId)
            .orElseThrow(() -> new woowacourse.cartitem.exception.InvalidCartItemException("장바구니를 찾을 수 없습니다."));
        cartItem.updateQuantity(quantity);

        cartItemDao.update(cartItemId, cartItem);
    }

    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final Long customerId = customerService.findIdByUsername(customerName);
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new InvalidCartItemException();
    }
}
