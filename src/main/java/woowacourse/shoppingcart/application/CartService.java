package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao,
      final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public List<CartItemResponse> showCartItems(final LoginCustomer customer) {
        final List<CartItem> cartIds = findCartIdsByCustomerName(customer.getNickname());
        return findCartItems(cartIds);
    }

    private List<CartItemResponse> findCartItems(List<CartItem> cartItems) {
        final List<CartItemResponse> cartItemResponses = new ArrayList<>();
        for (final CartItem cartItem : cartItems) {
            final Product product = productDao.findProductById(cartItem.getProductId());
            cartItemResponses.add(toCartItemResponse(cartItem, product));
        }
        return cartItemResponses;
    }

    private CartItemResponse toCartItemResponse(CartItem cartItem, Product product) {
        return new CartItemResponse(cartItem.getId(), product.getName(), product.getPrice(),
          cartItem.getQuantity(), product.getImageUrl());
    }

    private List<CartItem> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByNickname(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
    }

    public Long addCart(final Long productId, final String customerName) {
        final Long customerId = customerDao.findIdByNickname(customerName);
        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(final String customerName, final Long cartId) {
        validateCustomerCart(cartId, customerName);
        cartItemDao.deleteCartItem(cartId);
    }

    private void validateCustomerCart(final Long cartId, final String customerName) {
        final List<CartItem> cartItems = findCartIdsByCustomerName(customerName);
        cartItems.stream()
          .filter(i -> i.matchId(cartId))
          .findAny()
          .orElseThrow(NotInCustomerCartItemException::new);
    }
}
