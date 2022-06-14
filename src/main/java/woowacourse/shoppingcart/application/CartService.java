package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public CartItemsResponse findCartItemsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        List<CartItem> cartItems = cartItemDao.findAllByCustomerId(customerId);
        List<CartItemResponse> cartItemResponses = cartItems.stream()
            .map(CartItemResponse::from)
            .collect(Collectors.toList());
        return new CartItemsResponse(cartItemResponses);
    }

    private List<Long> findCartItemIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public CartItemResponse addCart(final Long productId, final int quantity, final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        Product product = productDao.findProductById(productId);
        removeProductQuantity(product, quantity);
        try {
            Long cartItemId = cartItemDao.addCartItem(customerId, productId, quantity);
            return new CartItemResponse(
                cartItemId,
                productId,
                product.getName(),
                product.getPrice(),
                quantity,
                product.getImageUrl()
            );
        } catch (DataAccessException e) {
            throw new InvalidProductException();
        }
    }

    private void removeProductQuantity(Product product, int quantity) {
        product.removeQuantity(quantity);
        updateProduct(product);
    }

    public void deleteCart(final String customerName, final Long cartItemId) {
        validateCustomerCart(cartItemId, customerName);
        CartItem cartItem = cartItemDao.findById(cartItemId);
        Product product = productDao.findProductById(cartItemDao.findProductIdById(cartItemId));
        addProductQuantity(product, cartItem.getQuantity());
        cartItemDao.deleteCartItem(cartItemId);
    }

    private void addProductQuantity(Product product, int quantity) {
        product.addQuantity(quantity);
        updateProduct(product);
    }

    private void updateProduct(Product product) {
        try {
            productDao.update(product);
        } catch (DataAccessException e) {
            throw new InvalidProductException();
        }
    }

    private void validateCustomerCart(final Long cartItemId, final String customerName) {
        final List<Long> cartItemIds = findCartItemIdsByCustomerName(customerName);
        if (cartItemIds.contains(cartItemId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    public void updateQuantity(final Long cartItemId, final int quantity) {
        CartItem cartItem = cartItemDao.findById(cartItemId);
        Product product = productDao.findProductById(cartItem.getProductId());

        product.addQuantity(cartItem.getQuantity() - quantity);
        cartItem.updateQuantity(quantity);

        productDao.update(product);
        cartItemDao.update(cartItem);
    }
}
