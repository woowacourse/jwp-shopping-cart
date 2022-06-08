package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartItemService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public CartItemResponse add(final LoginCustomer loginCustomer, final CartItemCreateRequest cartItemCreateRequest) {
        final Product product = productDao.findProductById(cartItemCreateRequest.getProductId());
        final Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        final CartItem cartItem = cartItemCreateRequest.toCartItem(product);
        List<Long> cartProductIds = cartItemDao.findProductIdsByCustomerId(customer.getId());

        if (cartProductIds.contains(product.getId())) {
            throw new IllegalArgumentException("이미 장바구니에 상품이 존재합니다.");
        }
        Long cartItemId = cartItemDao.add(customer.getId(), cartItem);
        return CartItemResponse.of(cartItemId, cartItem);
    }

    public List<CartItem> findCartsByCustomerName(final LoginCustomer loginCustomer) {
        final List<Long> cartIds = findCartIdsByCustomerName(loginCustomer);

        final List<CartItem> cartItems = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            cartItems.add(new CartItem(cartId, product));
        }
        return cartItems;
    }

    private List<Long> findCartIdsByCustomerName(final LoginCustomer loginCustomer) {
        final Long customerId = customerDao.findByLoginId(loginCustomer.getLoginId()).getId();
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public CartItemResponse updateQuantity(LoginCustomer loginCustomer, Long cartItemId,
                                           CartItemUpdateRequest cartItemUpdateRequest) {
        customerDao.findByLoginId(loginCustomer.getLoginId());
        final Long productId = cartItemDao.findProductIdById(cartItemId);
        final Product product = productDao.findProductById(productId);

        int quantity = cartItemUpdateRequest.getQuantity();
        cartItemDao.updateById(quantity, cartItemId);
        return CartItemResponse.of(cartItemId, new CartItem(cartItemId, product, quantity));
    }

    public void deleteAll(LoginCustomer loginCustomer) {
        final Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        cartItemDao.deleteAllByCustomerId(customer.getId());
    }

    public void deleteCart(final LoginCustomer loginCustomer, final Long id) {
        validateCustomerCart(id, loginCustomer);
        cartItemDao.deleteCartItem(id);
    }

    private void validateCustomerCart(final Long cartId, final LoginCustomer loginCustomer) {
        final List<Long> cartIds = findCartIdsByCustomerName(loginCustomer);
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
