package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Id;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.CartProducts;
import woowacourse.shoppingcart.dto.CartProductRequest;
import woowacourse.shoppingcart.dto.CartProductResponse;
import woowacourse.shoppingcart.dto.DeleteProductRequest;
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

    public Long addCart(final CartProductRequest cartProductRequest, final String customerName) {
        final Long customerId = customerDao.findByUsername(customerName).getId();
        try {
            return cartItemDao.addCartItem(customerId, cartProductRequest.getProductId(), cartProductRequest.getQuantity(), cartProductRequest.isChecked());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public CartProducts getCart(String customerName) {
        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
        List<CartProductResponse> cartEntities = new ArrayList<>();
        try {
            for (Long id : cartIds) {
                cartEntities.add(cartItemDao.findCartIdById(id));
            }
            return new CartProducts(cartEntities);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    private List<Long> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findByUsername(customerName).getId();
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public void deleteCart(DeleteProductRequest deleteProductRequest) {
        for (Id id : deleteProductRequest.getProducts()) {
            cartItemDao.deleteCartItem(id.getId());
        }
    }

    public void deleteAll() {
        cartItemDao.deleteAll();
    }
}
