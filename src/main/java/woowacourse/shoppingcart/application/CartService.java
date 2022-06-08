package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.entity.CartItemEntity;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.CartAdditionRequest;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(readOnly = true)
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
        final List<Long> cartIds = findCartIdsByCustomerName(email);

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final CartItemEntity cartItemEntity = cartItemDao.findById(cartId);
            final Product product = productDao.findProductById(cartItemEntity.getProductId());
            carts.add(new Cart(product, cartItemEntity.getQuantity()));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerName(final String email) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    @Transactional
    public void addCart(final CartAdditionRequest cartAdditionRequest, final String email) {
        final Long customerId = customerDao.findIdByEmail(new Email(email));
        try {
            cartItemDao.addCartItem(customerId, cartAdditionRequest.getProductId(), cartAdditionRequest.getQuantity());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    @Transactional
    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
