package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.cartItem.CartItemDeleteRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemResponse;
import woowacourse.shoppingcart.dto.cartItem.CartItemUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CartItemDao;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CustomerDao;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.ProductDao;

@Service
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartItemService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<CartItemResponse> findCartItemsByCustomerId(final long customerId) {
        Customer customer = getByCustomerId(customerId);
        List<CartItem> cartItems = cartItemDao.findByCustomerId(customer.getId());

        return CartItemResponse.from(cartItems);
    }

    public Long addCartItem(final long customerId, final long productId,
                            final CartItemUpdateRequest cartItemUpdateRequest) {
        Customer customer = getByCustomerId(customerId);
        Product product = getByProductId(productId);
        return cartItemDao.save(new CartItem(customer.getId(), product, cartItemUpdateRequest.getQuantity()));
    }

    public void deleteCartItemsByCustomerId(final long customerId, final CartItemDeleteRequest cartItemDeleteRequest) {
        Customer customer = getByCustomerId(customerId);
        validateCustomerCart(customer.getId(), cartItemDeleteRequest);
        cartItemDao.deleteCartItemsByCustomerId(customer.getId(), cartItemDeleteRequest.getProductIds());
    }

    private void validateCustomerCart(final long customerId, final CartItemDeleteRequest cartItemDeleteRequest) {
        final List<CartItem> cartItems = cartItemDao.findByCustomerId(customerId);
        List<Long> deleteProductIds = cartItemDeleteRequest.getProductIds();

        if (!isContainingAll(cartItems, deleteProductIds)) {
            throw new NotInCustomerCartItemException();
        }
    }

    private boolean isContainingAll(List<CartItem> cartItems, List<Long> deleteProductIds) {
        return deleteProductIds.stream()
                .allMatch(deleteProductId -> isContain(cartItems, deleteProductId));
    }

    private boolean isContain(List<CartItem> cartItems, Long deleteProductId) {
        return cartItems.stream()
                .anyMatch(cartItem -> cartItem.isSameId(deleteProductId));
    }

    private Product getByProductId(long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(InvalidProductException::new);
    }

    private Customer getByCustomerId(long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
    }
}
