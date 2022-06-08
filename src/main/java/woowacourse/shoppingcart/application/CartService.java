package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartResponses;
import woowacourse.shoppingcart.dto.DeleteProductRequest;
import woowacourse.shoppingcart.dto.UpdateCartRequests;
import woowacourse.shoppingcart.exception.InvalidProductException;

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
    public Long addCart(String username, CartRequest cartRequest) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        try {
            Product product = productDao.findProductById(cartRequest.getProductId());
            Cart cart = new Cart(product, cartRequest.getQuantity(), cartRequest.getChecked());
            return cartItemDao.addCartItem(customerId, cart).getId();
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public CartResponses findCartsByUsername(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        List<Cart> carts = cartItemDao.findByCustomerId(customerId);
        return CartResponses.from(carts);
    }

    @Transactional(rollbackFor = Exception.class)
    public CartResponses updateCartItems(String username, UpdateCartRequests updateCartRequests) {
        List<Cart> carts = updateCartRequests.carts();
        cartItemDao.updateCartItem(carts);
        List<Cart> foundCarts = findCartIdsByUsername(username);
        List<CartResponse> cartResponses = foundCarts.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
        return new CartResponses(cartResponses);
    }

    private List<Cart> findCartIdsByUsername(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        return cartItemDao.findByCustomerId(customerId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCart(DeleteProductRequest deleteProductRequest) {
        List<Long> cartIds = deleteProductRequest.cartIds();
        cartItemDao.deleteCartItem(cartIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAllCart(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        cartItemDao.deleteAllCartItem(customerId);
    }
}
