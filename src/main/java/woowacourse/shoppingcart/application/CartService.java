package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartResponses;
import woowacourse.shoppingcart.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemRequests;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;
import woowacourse.shoppingcart.dto.UpdateCartItemRequests;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
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

    @Transactional(readOnly = true)
    public CartResponses findCartsByCustomerName(final String customerName) {
        final List<Cart> carts = findAllByCustomerName(customerName);

        final List<CartResponse> responses = new ArrayList<>();
        for (final Cart cart : carts) {
            Product product = productDao.findProductById(cart.getProductId());
            responses.add(CartResponse.fromCartAndProduct(cart, product));
        }
        return new CartResponses(responses);
    }

    private List<Cart> findAllByCustomerName(final String customerName) {
        final Long customerId = customerDao.findByUsername(customerName).getId();
        return cartItemDao.findAllByCustomerId(customerId);
    }

    @Transactional
    public Long addCart(final AddCartItemRequest updateCartItemRequest, final String customerName) {
        final Long customerId = customerDao.findByUsername(customerName).getId();
        try {
            Cart cart = cartItemDao.findByCustomerIdAndProductId(customerId, updateCartItemRequest.getProductId());
            cartItemDao.increaseQuantityById(cart.getId(), updateCartItemRequest.getQuantity());
            return cart.getId();
        } catch (InvalidCartItemException e) {
            return cartItemDao.addCartItem(
                    customerId,
                    updateCartItemRequest.getProductId(),
                    updateCartItemRequest.getQuantity(),
                    updateCartItemRequest.getChecked());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    @Transactional
    public CartResponses updateCartItems(UpdateCartItemRequests updateCartItemRequests, String customerName) {
        List<CartResponse> responses = new ArrayList<>();

        for (UpdateCartItemRequest cartItemRequest : updateCartItemRequests.getCartItems()) {
            validateCustomerCart(cartItemRequest.getId(), customerName);

            Cart cart = cartItemDao.findById(cartItemRequest.getId());
            cart.update(cartItemRequest.getQuantity(), cartItemRequest.getChecked());
            cartItemDao.update(cart.getId(), cart);

            Product product = productDao.findProductById(cart.getProductId());
            responses.add(CartResponse.fromCartAndProduct(cart, product));
        }

        return new CartResponses(responses);
    }

    @Transactional
    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<Long> cartIds = findAllByCustomerName(customerName).stream()
                .map(Cart::getId)
                .collect(Collectors.toList());
        if (cartIds.contains(cartId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    @Transactional
    public void deleteAllCart(String customerName) {
        Long customerId = customerDao.findByUsername(customerName).getId();
        cartItemDao.deleteAllByCustomerId(customerId);
    }

    @Transactional
    public void deleteAllCartByProducts(String customerName, DeleteCartItemRequests deleteCartItemRequests) {
        List<DeleteCartItemRequest> cartItems = deleteCartItemRequests.getCartItems();
        for (DeleteCartItemRequest cartItem : cartItems) {
            long id = cartItem.getId();
            validateCustomerCart(id, customerName);
            deleteCart(customerName, id);
        }
    }
}
