package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dao.dto.CartItemDto;
import woowacourse.shoppingcart.exception.AlreadyExistCartItemException;
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

    public List<Cart> findCartsByCustomerName(final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    public List<CartItem> findCartItems(final Long customerId) {
        List<CartItemDto> cartItemDtos = cartItemDao.findCartItemByCustomerId(customerId);
        return cartItemDtos.stream()
                .map(it -> new CartItem(productDao.findProductById(it.getProductId()), it.getQuantity()))
                .collect(Collectors.toList());
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long customerId, final Long productId) {
        if (cartItemDao.isExistItem(customerId, productId)) {
            throw new AlreadyExistCartItemException();
        }

        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final Long customerId, final List<Long> productIds) {
        validateCustomerCart(customerId, productIds);
        productIds.forEach(it -> cartItemDao.deleteCartItem(customerId, it));
    }

    private void validateCustomerCart(final Long customerId, final List<Long> productIds) {
        final List<Long> existingProductIds = cartItemDao.findProductIdsByCustomerId(customerId);
        boolean allExistProduct = existingProductIds.containsAll(productIds);
        if (!allExistProduct) {
            throw new NotInCustomerCartItemException();
        }
    }

    public void updateQuantity(Long customerId, Long productId, int quantity) {
        if (!cartItemDao.isExistItem(customerId, productId)) {
            throw new InvalidProductException();
        }
        cartItemDao.updateQuantity(customerId, productId, quantity);
    }

    public void emptyCart(Long customerId) {
        cartItemDao.deleteAllCartItem(customerId);
    }
}
