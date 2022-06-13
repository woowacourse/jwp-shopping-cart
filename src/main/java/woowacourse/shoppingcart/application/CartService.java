package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.dto.request.CartRequest;
import woowacourse.shoppingcart.dto.request.DeleteProductRequest;
import woowacourse.shoppingcart.dto.request.UpdateCartRequests;
import woowacourse.shoppingcart.dto.response.CartResponses;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(CartItemDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addCart(String username, CartRequest cartRequest) {
        validateQuantity(cartRequest.getQuantity());
        validateChecked(cartRequest.getChecked());
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        Product product = productDao.findProductById(cartRequest.getProductId());

        if (cartItemDao.existByProductId(customerId, cartRequest.getProductId())) {
            cartItemDao.updateCartItemByProductId(customerId, new Cart(product, cartRequest.getQuantity() + 1, cartRequest.getChecked()));
            return;
        }

        Cart cart = new Cart(product, cartRequest.getQuantity(), cartRequest.getChecked());
        cartItemDao.addCartItem(customerId, cart);
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

    public CartResponses findCartsByUsername(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        List<Cart> carts = cartItemDao.findByCustomerId(customerId);
        return CartResponses.from(carts);
    }

    @Transactional(rollbackFor = Exception.class)
    public CartResponses updateCartItems(String username, UpdateCartRequests updateCartRequests) {
        List<Cart> carts = updateCartRequests.toCart();
        cartItemDao.updateCartItems(carts);

        List<Cart> updatedCarts = getUpdatedCarts(username, updateCartRequests);
        return CartResponses.from(updatedCarts);
    }

    private List<Cart> getUpdatedCarts(String username, UpdateCartRequests updateCartRequests) {
        List<Long> cartIds = updateCartRequests.toCartIds();
        List<Cart> foundCarts = getCartIdsByUsername(username);
        return foundCarts.stream()
                .filter(it -> cartIds.contains(it.getId()))
                .collect(Collectors.toList());
    }

    private List<Cart> getCartIdsByUsername(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        return cartItemDao.findByCustomerId(customerId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCart(String username, DeleteProductRequest deleteProductRequest) {
        Customer customer = customerDao.findByUsername(new Username(username));
        List<Long> deleteCartIds = deleteProductRequest.cartIds();
        List<Cart> carts = cartItemDao.findByCustomerId(customer.getId());
        List<Long> cartIds = getCartIds(carts);

        if (!cartIds.containsAll(deleteCartIds)) {
            throw new InvalidCartItemException();
        }
        cartItemDao.deleteCartItems(deleteCartIds);
    }

    private List<Long> getCartIds(List<Cart> carts) {
        return carts.stream()
                .map(Cart::getId)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAllCart(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        cartItemDao.deleteAllCartItem(customerId);
    }
}
