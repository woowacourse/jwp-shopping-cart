package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;

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

    public Long addCart(final int customerId, final Long productId, final int quantity) {
        return cartItemDao.addCartItem(customerId, productId, quantity);
    }

//    public List<Cart> findCartsByCustomerName(final String customerName) {
//        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
//
//        final List<Cart> carts = new ArrayList<>();
//        for (final Long cartId : cartIds) {
//            final Long productId = cartItemDao.findProductIdById(cartId);
//            final Product product = productDao.findProductById(productId);
//            carts.add(new Cart(cartId, product));
//        }
//        return carts;
//    }
////
//    private List<Long> findCartIdsByCustomerName(final String email) {
//        final int customerId = customerDao.findByEmail(email).getId();
//        return cartItemDao.findIdsByCustomerId((long) customerId);
//    }
//

//    public void deleteCart(final String customerName, final Long cartId) {
//        validateCustomerCart(cartId, customerName);
//        cartItemDao.deleteCartItem(cartId);
//    }
//
//    private void validateCustomerCart(final Long cartId, final String customerName) {
//        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
//        if (cartIds.contains(cartId)) {
//            return;
//        }
//        throw new NotInCustomerCartItemException();
//    }
}
