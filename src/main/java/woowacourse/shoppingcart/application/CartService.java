package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ChangeCartItemQuantityRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
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

    public List<CartItemResponse> showCartItems(final Customer customer) {
        final List<CartItem> cartIds = findCartIdsByCustomerName(customer.getNickname());
        return findCartItems(cartIds);
    }

    private List<CartItem> findCartIdsByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByNickname(customerName);
        return cartItemDao.findIdsByCustomerId(customerId);
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

    public Long addCart(Customer customer, Long productId) {
        final Long customerId = customerDao.findIdByNickname(customer.getNickname());
        try {
            return cartItemDao.addCartItem(customerId, productId);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void deleteCart(Customer customer, Long productId) {
        validateCustomerCart(customer.getNickname(), productId);
        cartItemDao.deleteCartItem(customer.getId(), productId);
    }

    private void validateCustomerCart(String nickname, Long productId) {
        final List<CartItem> cartItems = findCartIdsByCustomerName(nickname);
        cartItems.stream()
          .filter(i -> i.matchId(productId))
          .findAny()
          .orElseThrow(NotInCustomerCartItemException::new);
    }

    public void changeQuantity(Customer customer, ChangeCartItemQuantityRequest request, Long productId) {
        validateQuantityNegativeNumber(request.getQuantity());
        cartItemDao.updateQuantityById(customer.getId(), request.getQuantity(), productId);
    }

    private void validateQuantityNegativeNumber(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("장바구니 상품 수량을 음수로 수정할 수 없습니다.");
        }
    }
}
