package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.cartitem.CartItemResponses;
import woowacourse.shoppingcart.dto.cartitem.CartItemSaveRequest;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartItemService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public CartItemResponses findCartsByCustomerName(final String customerName) {
        final List<Long> cartItemIds = findCartIdsByCustomerName(customerName);

        final List<CartItem> cartItems = new ArrayList<>();
        for (final Long cartItemId : cartItemIds) {
            final Long productId = cartItemDao.findProductIdById(cartItemId);
            final int quantity = cartItemDao.findQuantityById(cartItemId);
            final Product product = productDao.findProductById(productId);
            cartItems.add(new CartItem(cartItemId, product, quantity));
        }
        return CartItemResponses.from(cartItems);
    }

    @Transactional
    public Long addCart(final CartItemSaveRequest request, final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        checkAvaliableForPurchaseProduct(request.getProductId(), request.getQuantity());
        try {
            return cartItemDao.addCartItem(customerId, request.getProductId(), request.getQuantity());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    @Transactional
    public void updateCartItemQuantity(final String customerName, final Long cartItemId, final int quantity) {
        validateCustomerCart(cartItemId, customerName);
        checkAvaliableForPurchaseProduct(cartItemDao.findProductIdById(cartItemId), quantity);
        cartItemDao.updateCartItemQuantity(cartItemId, quantity);
    }

    @Transactional
    public void deleteCart(final String customerName, final Long cartItemId) {
        validateCustomerCart(cartItemId, customerName);
        cartItemDao.deleteCartItem(cartItemId);
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    private void checkAvaliableForPurchaseProduct(final Long productId, final int quantity) {
        final Product product = productDao.findProductById(productId);
        product.purchaseProduct(quantity);
    }

    private void validateCustomerCart(final Long cartItemId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartItemId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
