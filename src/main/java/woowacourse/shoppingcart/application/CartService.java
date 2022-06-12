package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemsResponse;
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

    @Transactional
    public Long add(final String username, final CartItemAddRequest request) {
        final Long customerId = customerDao.findIdByUserName(username);
        Product product = productDao.findProductById(request.getProductId());
        CartItem cartItem = new CartItem(product, request.getQuantity());
        try {
            return cartItemDao.save(customerId, cartItem);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public CartItem findById(final Long cartItemId) {
        return cartItemDao.findById(cartItemId);
    }

    public CartItemsResponse findAllByUsername(final String username) {
        Long customerId = customerDao.findIdByUserName(username);
        return new CartItemsResponse(cartItemDao.findAllByCustomerId(customerId));
    }

    @Transactional
    public void updateQuantity(final Long cartItemId, final int quantity) {
        CartItem cartItem = cartItemDao.findById(cartItemId);
        cartItem.changeQuantity(quantity);
        cartItemDao.updateQuantity(cartItem);
    }

    public void deleteOneById(final String username, final Long cartItemId) {
        validateCustomerCart(cartItemId, username);
        cartItemDao.deleteById(cartItemId);
    }

    private void validateCustomerCart(final Long cartItemId, final String username) {
        Customer customer = customerDao.findByUsername(username);
        if (!cartItemDao.isCartItemExistByCustomer(cartItemId, customer.getId())) {
            throw new NotInCustomerCartItemException();
        }
    }
}
