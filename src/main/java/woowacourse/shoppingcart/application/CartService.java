package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;
import woowacourse.shoppingcart.exception.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<Cart> findCartsByCustomer(final Customer customer) {
        final List<Long> cartIds = findCartIdsByCustomerId(customer.getId());

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId)
                    .orElseThrow(InvalidProductException::new);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    private List<Long> findCartIdsByCustomerId(final Long customerId) {
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final Customer customer) {
        productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);
        validateDuplicateCartItem(productId);
        try {
            return cartItemDao.addCartItem(customer.getId(), productId, 1);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private void validateDuplicateCartItem(Long productId) {
        if (cartItemDao.existByProductId(productId)) {
            throw new DuplicateCartItemException();
        }
    }

    public void deleteCart(final Customer customer, final Long productId) {
        productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);

        cartItemDao.deleteByProductIdAndCustomerId(customer.getId(), productId);
    }

    public Cart updateQuantity(CartItemUpdateRequest request, final Customer customer, final Long productId) {
        validateQuantity(request.getQuantity());
        Product product = productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);

        cartItemDao.updateQuantity(customer.getId(), productId, request.getQuantity());

        return new Cart(product, request.getQuantity());

    }

    private void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new InvalidQuantityException();
        }
    }
}
