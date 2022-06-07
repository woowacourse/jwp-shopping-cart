package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<CartItem> findCartsByCustomerName(LoginCustomer loginCustomer) {
        List<Long> cartIds = findCartIdsByCustomerName(loginCustomer.getUsername());

        List<CartItem> cartItems = new ArrayList<>();
        for (Long cartId : cartIds) {
            Long productId = cartItemDao.findProductIdById(cartId);
            Product product = productDao.findProductById(productId);
            cartItems.add(new CartItem(cartId, product));
        }
        return cartItems;
    }

    private List<Long> findCartIdsByCustomerName(String customerName) {
        Long customerId = customerDao.findIdByUsername(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(Long productId, LoginCustomer loginCustomer) {
        Long customerId = customerDao.findIdByUsername(loginCustomer.getUsername());
        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(LoginCustomer loginCustomer, Long cartId) {
        validateCustomerCart(cartId, loginCustomer.getUsername());
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(Long cartId, String username) {
        List<Long> cartIds = findCartIdsByCustomerName(username);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
