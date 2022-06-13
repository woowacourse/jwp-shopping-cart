package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
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

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public void addCart(String username, CartRequest cartRequest) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        Product product = productDao.findProductById(cartRequest.getProductId());

        if (cartItemDao.existByProductId(customerId, cartRequest.getProductId())) {
            cartItemDao.increaseQuantity(customerId,
                    new CartItem(product, cartRequest.getQuantity(), cartRequest.getChecked()));
            return;
        }

        CartItem cartItem = new CartItem(product, cartRequest.getQuantity(), cartRequest.getChecked());
        cartItemDao.addCartItem(customerId, cartItem);
    }

    public CartResponse findCartByUsername(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        Cart cart = cartItemDao.findByCustomerId(customerId);
        return CartResponse.from(cart);
    }

    @Transactional
    public CartResponse updateCartItems(String username, UpdateCartRequests updateCartRequests) {
        List<Long> updateCartIds = updateCartRequests.toCartIds();
        validateCart(username, updateCartIds);

        List<CartItem> cartItems = updateCartRequests.toCart();
        cartItemDao.updateCartItems(cartItems);
        List<CartItem> updatedCartItems = getUpdatedCart(username, updateCartIds);
        return CartResponse.from(updatedCartItems);
    }

    private void validateCart(String username, List<Long> cartIdsWithRequest) {
        Customer customer = customerDao.findByUsername(new Username(username));
        Cart cart = cartItemDao.findByCustomerId(customer.getId());
        List<Long> cartIds = cart.getCartItemIds();

        if (!cartIds.containsAll(cartIdsWithRequest)) {
            throw new InvalidCartItemException();
        }
    }

    private List<CartItem> getUpdatedCart(String username, List<Long> cartIds) {
        Cart cart = getCartByUsername(username);
        return cart.getExistingCartItem(cartIds);
    }

    private Cart getCartByUsername(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        return cartItemDao.findByCustomerId(customerId);
    }

    @Transactional
    public void deleteCartItem(String username, DeleteProductRequest deleteProductRequest) {
        List<Long> deleteCartIds = deleteProductRequest.toCartIds();
        validateCart(username, deleteCartIds);
        cartItemDao.deleteCartItems(deleteCartIds);
    }

    @Transactional
    public void deleteAllCartItem(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        cartItemDao.deleteAllCartItem(customerId);
    }
}
