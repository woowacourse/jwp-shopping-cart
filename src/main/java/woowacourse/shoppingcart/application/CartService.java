package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.entity.CartEntity;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
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

    public List<Cart> findCartsByEmail(final String email) {
        final List<Long> cartIds = findCartIdsByEmail(email);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            final int quantity = cartItemDao.findProductQuantityById(cartId);
            carts.add(new Cart(cartId, product, quantity));
        }
        return carts;
    }

    private List<Long> findCartIdsByEmail(final String email) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final CartRequest cartRequest, final String email) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        final Product product = productDao.findProductById(cartRequest.getProductId());
        Optional<CartEntity> cartEntity = cartItemDao.findQuantityByCustomerIdAndProductId(
                customerId,
                cartRequest.getProductId());
        if (cartEntity.isEmpty()) {
            return cartItemDao.addCartItem(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        }

        final int newQuantity = cartEntity.get().getQuantity() + cartRequest.getQuantity();
        if (product.getStock() < newQuantity) {
            throw new InvalidCartItemException("상품의 수량이 부족합니다. 현재 재고 = " + product.getStock());
        }
        cartItemDao.modifyQuantity(customerId, cartRequest.getProductId(), newQuantity);
        return cartEntity.get().getId();
    }

    public List<Cart> modifyCartQuantity(CartRequest cartRequest, String email) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        cartItemDao.modifyQuantity(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        return findCartsByEmail(email);
    }

    public List<Cart> deleteCart(final String email, final Long productId) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        validateCustomerCart(customerId, productId);
        cartItemDao.deleteCartItem(customerId, productId);
        return findCartsByEmail(email);
    }

    private void validateCustomerCart(final Long customerId, final Long productId) {
        List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);
        if (productIds.contains(productId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
