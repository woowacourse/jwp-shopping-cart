package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.cartitem.CartItemResponse;
import woowacourse.shoppingcart.dto.cartitem.CartItemSaveRequest;
import woowacourse.shoppingcart.dto.cartitem.CartItemsResponse;
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

    public CartItemsResponse findCartItemsByCustomerName(final String customerName) {
        final List<CartItem> cartItems = findCartItemIdsByCustomerName(customerName);
        return CartItemsResponse.from(cartItems);
    }

    @Transactional
    public CartItemResponse addCartItem(final CartItemSaveRequest request, final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        checkAvaliableForPurchaseProduct(request.getProductId(), request.getQuantity());
        try {
            Long savedId = cartItemDao.addCartItem(customerId, request.getProductId(), request.getQuantity());
            return CartItemResponse.from(cartItemDao.findById(savedId));
        } catch (Exception e) {
            throw new InvalidProductException("올바르지 않은 사용자 이름이거나 상품 아이디 입니다.");
        }
    }

    @Transactional
    public void updateCartItemQuantity(final String customerName, final Long cartItemId, final int quantity) {
        validateCustomerHasCartItem(cartItemId, customerName);
        checkAvaliableForPurchaseProduct(cartItemDao.findProductIdById(cartItemId), quantity);
        cartItemDao.updateCartItemQuantity(cartItemId, quantity);
    }

    @Transactional
    public void deleteCartItem(final String customerName, final Long cartItemId) {
        validateCustomerHasCartItem(cartItemId, customerName);
        cartItemDao.deleteCartItem(cartItemId);
    }

    private List<CartItem> findCartItemIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findAllByCustomerId(customerId);
    }

    private void checkAvaliableForPurchaseProduct(final Long productId, final int quantity) {
        final Product product = productDao.findProductById(productId);
        product.purchaseProduct(quantity);
    }

    private void validateCustomerHasCartItem(final Long cartItemId, final String customerName) {
        findCartItemIdsByCustomerName(customerName).stream()
                .map(CartItem::getId)
                .filter(id -> id.equals(cartItemId))
                .findAny()
                .orElseThrow(NotInCustomerCartItemException::new);
    }
}
