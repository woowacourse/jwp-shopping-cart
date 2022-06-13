package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.dto.request.CartRequest;
import woowacourse.shoppingcart.dto.request.DeleteProductRequest;
import woowacourse.shoppingcart.dto.request.UpdateCartRequests;
import woowacourse.shoppingcart.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartDao cartDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public void addCart(String username, CartRequest cartRequest) {
        validateQuantity(cartRequest.getQuantity());
        validateChecked(cartRequest.getChecked());
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        Product product = productDao.findProductById(cartRequest.getProductId());

        if (cartDao.existByProductId(customerId, cartRequest.getProductId())) {
            cartDao.updateCartItemByProductId(customerId, new CartItem(product, cartRequest.getQuantity() + 1, cartRequest.getChecked()));
            return;
        }

        CartItem cartItem = new CartItem(product, cartRequest.getQuantity(), cartRequest.getChecked());
        cartDao.addCartItem(customerId, cartItem);
    }

    private void validateQuantity(Integer quantity) {
        if (quantity < 0) {
            throw new InvalidCartItemException("수량은 0 이상이어야 합니다.");
        }
    }

    private void validateChecked(Boolean checked) {
        if (checked == null) {
            throw new InvalidCartItemException("구매 여부는 비어서는 안됩니다.");
        }
    }

    public CartResponse findCartByUsername(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        Cart cart = cartDao.findByCustomerId(customerId);
        return CartResponse.from(cart);
    }

    @Transactional
    public CartResponse updateCartItems(String username, UpdateCartRequests updateCartRequests) {
        List<CartItem> cartItems = updateCartRequests.toCart();
        cartDao.updateCartItems(cartItems);

        List<CartItem> updatedCartItems = getUpdatedCart(username, updateCartRequests.toCartIds());
        return CartResponse.from(updatedCartItems);
    }

    private List<CartItem> getUpdatedCart(String username, List<Long> cartIds) {
        Cart cart = getCartByUsername(username);
        return cart.getExistingCartItem(cartIds);
    }

    private Cart getCartByUsername(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        return cartDao.findByCustomerId(customerId);
    }

    @Transactional
    public void deleteCartItem(String username, DeleteProductRequest deleteProductRequest) {
        Customer customer = customerDao.findByUsername(new Username(username));
        List<Long> deleteCartIds = deleteProductRequest.toCartIds();
        Cart cart = cartDao.findByCustomerId(customer.getId());
        List<Long> cartIds = cart.getCartItemIds();

        if (!cartIds.containsAll(deleteCartIds)) {
            throw new InvalidCartItemException();
        }
        cartDao.deleteCartItems(deleteCartIds);
    }

    @Transactional
    public void deleteAllCartItem(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        cartDao.deleteAllCartItem(customerId);
    }
}
