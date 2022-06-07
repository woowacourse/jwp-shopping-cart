package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
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
            final int stock = product.getStock();
            optimizeQuantity(email, carts, cartId, productId, product, quantity, stock);
        }
        return carts;
    }

    private List<Long> findCartIdsByEmail(final String email) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    private void optimizeQuantity(String email, List<Cart> carts, Long cartId, Long productId, Product product,
                                  int quantity,
                                  int stock) {
        if (stock < quantity) {
            modifyCartQuantity(new CartRequest(productId, stock), email);
            carts.add(new Cart(cartId, product, stock));
            return;
        }
        carts.add(new Cart(cartId, product, quantity));
    }

    public Long addCart(final CartRequest cartRequest, final String email) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        Product product = productDao.findProductById(cartRequest.getProductId());
        if (product.getStock() < cartRequest.getQuantity()) {
            throw new InvalidCartItemException("상품의 수량이 부족합니다. 현재 재고 = " + product.getStock());
        }
        return cartItemDao.addCartItem(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
    }

    public List<Cart> modifyCartQuantity(CartRequest cartRequest, String email) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        cartItemDao.modifyQuantity(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        return findCartsByEmail(email);
    }

    public List<Cart> deleteCart(final String email, final Long cartId) {
        validateCustomerCart(cartId, email);
        cartItemDao.deleteCartItem(cartId);
        return findCartsByEmail(email);
    }

    private void validateCustomerCart(final Long cartId, final String email) {
        final List<Long> cartIds = findCartIdsByEmail(email);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
